package th.ac.kbu.cs.ExamProject.Domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.ExamResultAnswer;
import th.ac.kbu.cs.ExamProject.Exception.CantEditExamException;
import th.ac.kbu.cs.ExamProject.Exception.DataInValidException;
import th.ac.kbu.cs.ExamProject.Exception.ExamIsExpiredException;
import th.ac.kbu.cs.ExamProject.Exception.ExamPermissionDeniedException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.DoExamService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class SaveExamDomain extends DoExamPrototype {

	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private DoExamService doExamService;
	
	public void autoSave(){
		validParameter();
		ExamResult examResult = getExamResult(this.getExamResultId());
		List<ExamResultAnswer> examResultAnswers = convertExamData(this.getExamResultAnswerData());
		validateAnswerData(examResultAnswers,this.getExamResultId());
		doExamService.updateExamResult(examResult, examResultAnswers);
	}
	
	public void validParameter(){
		if(BeanUtils.isEmpty(this.getExamResultAnswerData())
				|| BeanUtils.isEmpty(this.getExamResultId())){
			throw new ParameterNotFoundException("Parameter not found!!");
		}
	}
	
	public ExamResult getExamResult(Long examResultId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResult.class,"examResult");
		criteria.add(Restrictions.eq("examResult.examResultId", examResultId));
		ExamResult examResult = basicFinderService.findUniqueByCriteria(criteria);
		if(!examResult.getUsername().equals(SecurityUtils.getUsername())){
			throw new ExamPermissionDeniedException("Permission denied!!");
		}
		if(examResult.getExamCompleted()){
			throw new CantEditExamException("Cant edit exam!!");
		}
		if(BeanUtils.isNotEmpty(examResult.getExamExpireDate())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(examResult.getExamExpireDate());
			cal.add(Calendar.MINUTE, 5);
			Date nowToday = new Date();
			if(nowToday.after(cal.getTime())){
				throw new ExamIsExpiredException("Exam is expired");
			}
		}
		return examResult;
	}
	
	
	public void validateAnswerData(List<ExamResultAnswer> examResultAnswers,Long examResultId){
		for(ExamResultAnswer examResultAnswer : examResultAnswers){
			if(examResultAnswer.getExamResultId() != examResultId){
				throw new DataInValidException("Data invalid");
			}
		}
	}
	
	private List<ExamResultAnswer> convertExamData(String examResultAnswerData){
		ObjectMapper mapper = new ObjectMapper();
		List<ExamResultAnswer> results = null;
		if(BeanUtils.isNotEmpty(examResultAnswerData)){
			try{
				results = mapper.readValue(examResultAnswerData, new TypeReference<ArrayList<ExamResultAnswer>>(){});
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return results;
	}

	public void sendExam() {
		validParameter();
		ExamResult examResult = getExamResult(this.getExamResultId());
		setCompleteExam(examResult);
		List<ExamResultAnswer> examResultAnswers = convertExamData(this.getExamResultAnswerData());
		validateAnswerData(examResultAnswers,this.getExamResultId());
		doExamService.updateExamResult(examResult, examResultAnswers);
	}
	
	private void setCompleteExam(ExamResult examResult){
		Date nowToday = new Date();
		examResult.setExamCompleted(true);
		examResult.setExamCompleteDate(nowToday);
		
		Long usedTimeMillis = nowToday.getTime() - examResult.getExamStartDate().getTime();
		Integer usedTimeSec = BeanUtils.toInteger(Math.floor(usedTimeMillis.doubleValue() / 1000));
		examResult.setExamUsedTime(usedTimeSec);
	}

	public Boolean sendExamFromSelect() {
		validParameterFromSelect();
		ExamResult examResult = this.getExamResultFromSelect(this.getExamResultId());
		examResult.setExamCompleted(true);
		Date nowToday = new Date();
		examResult.setExamCompleteDate(nowToday);
		Integer usedTime = BeanUtils.toInteger(nowToday.getTime() - examResult.getExamStartDate().getTime());
		examResult.setExamUsedTime(usedTime);
		
		Boolean isExpired = false;
		if(BeanUtils.isNotEmpty(examResult.getExamExpireDate())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(examResult.getExamExpireDate());
			cal.add(Calendar.MINUTE, 5);
			if(nowToday.after(cal.getTime())){
				isExpired = true;
			}
		}
		
		doExamService.setCompleteExamFromSelect(examResult, isExpired);
		return isExpired;
	}

	private void validParameterFromSelect() {
		if(BeanUtils.isEmpty(this.getExamResultId())){
			throw new ParameterNotFoundException("Parameter not found!!");
		}
	}
	private ExamResult getExamResultFromSelect(Long examResultId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResult.class,"examResult");
		criteria.add(Restrictions.eq("examResult.examResultId", examResultId));
		ExamResult examResult = basicFinderService.findUniqueByCriteria(criteria);
		if(!examResult.getUsername().equals(SecurityUtils.getUsername())){
			throw new ExamPermissionDeniedException("Permission denied!!");
		}
		if(examResult.getExamCompleted()){
			throw new CantEditExamException("Cant edit exam!!");
		}
		return examResult;
	}
}
