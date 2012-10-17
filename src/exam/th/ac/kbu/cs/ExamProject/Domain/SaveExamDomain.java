package th.ac.kbu.cs.ExamProject.Domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.ExamResultAnswer;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
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
		doExamService.updateExamResult(examResult, examResultAnswers,false,true);
	}
	
	public void validParameter(){
		if(BeanUtils.isEmpty(this.getExamResultAnswerData())
				|| BeanUtils.isEmpty(this.getExamResultId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public ExamResult getExamResult(Long examResultId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResult.class,"examResult");
		criteria.add(Restrictions.eq("examResult.examResultId", examResultId));
		ExamResult examResult = basicFinderService.findUniqueByCriteria(criteria);
		if(!examResult.getUsername().equals(SecurityUtils.getUsername())){
			throw new CoreException(CoreExceptionMessage.PERMISSION_DENIED);
		}
		if(examResult.getExamCompleted()){
			throw new CoreException(CoreExceptionMessage.CANT_EDIT_EXAM);
		}
		if(BeanUtils.isNotEmpty(examResult.getExamExpireDate())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(examResult.getExamExpireDate());
			cal.add(Calendar.MINUTE, 3);
			Date nowToday = new Date();
			if(nowToday.after(cal.getTime())){
				throw new CoreException(CoreExceptionMessage.EXAM_EXPIRED);
			}
		}
		return examResult;
	}
	
	
	public void validateAnswerData(List<ExamResultAnswer> examResultAnswers,Long examResultId){
		for(ExamResultAnswer examResultAnswer : examResultAnswers){
			if(examResultAnswer.getExamResultId() != examResultId){
				throw new CoreException(CoreExceptionMessage.INVALID_DATA);
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
		Boolean isExpired = setCompleteExam(examResult);
		List<ExamResultAnswer> examResultAnswers = convertExamData(this.getExamResultAnswerData());
		validateAnswerData(examResultAnswers,this.getExamResultId());
		doExamService.updateExamResult(examResult, examResultAnswers,isExpired,false);
	}
	
//	private void calScore(ExamResult examResult,Boolean isExpired){
//		if(isExpired){
//			examResult.setExamScore(0F);
//		}else{
//			DetachedCriteria criteria = DetachedCriteria.forClass(ExamResultAnswer.class,"examResultAnswer");
//			criteria.createAlias("examResultAnswer.answer","answer");
//			ProjectionList projectionList = Projections.projectionList();
//			projectionList.add(Projections.rowCount());
//			criteria.setProjection(projectionList);
//			criteria.add(Restrictions.eq("examResultAnswer.examResultId", examResult.getExamResultId()));
//			criteria.add(Restrictions.ge("answer.answerScore", 1));
//			Integer score = BeanUtils.toInteger(basicFinderService.findUniqueByCriteria(criteria));
//		
//			examResult.setExamScore(score);
//		}
//	}
	
	private Boolean setCompleteExam(ExamResult examResult){
		Date nowToday = new Date();
		examResult.setExamCompleted(true);
		examResult.setExamCompleteDate(nowToday);
		
		Long usedTimeMillis = nowToday.getTime() - examResult.getExamStartDate().getTime();
		Integer usedTimeSec = BeanUtils.toInteger(Math.floor(usedTimeMillis.doubleValue() / 1000));
		examResult.setExamUsedTime(usedTimeSec);
		

		Boolean isExpired = false;
		if(BeanUtils.isNotEmpty(examResult.getExamExpireDate())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(examResult.getExamExpireDate());
			cal.add(Calendar.MINUTE, 3);
			if(nowToday.after(cal.getTime())){
				isExpired = true;
			}
		}
		return isExpired;
	}

	public Boolean finishExamFromSelect() {
		validParameterFromSelect();
		ExamResult examResult = this.getExamResultFromSelect(this.getExamResultId());
		Boolean isExpired = setCompleteExam(examResult);
		doExamService.setCompleteExamFromSelect(examResult, isExpired);
		return isExpired;
	}

	private void validParameterFromSelect() {
		if(BeanUtils.isEmpty(this.getExamResultId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	private ExamResult getExamResultFromSelect(Long examResultId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResult.class,"examResult");
		criteria.add(Restrictions.eq("examResult.examResultId", examResultId));
		ExamResult examResult = basicFinderService.findUniqueByCriteria(criteria);
		if(!examResult.getUsername().equals(SecurityUtils.getUsername())){
			throw new CoreException(CoreExceptionMessage.PERMISSION_DENIED);
		}
		if(examResult.getExamCompleted()){
			throw new CoreException(CoreExceptionMessage.CANT_EDIT_EXAM);
		}
		return examResult;
	}
}
