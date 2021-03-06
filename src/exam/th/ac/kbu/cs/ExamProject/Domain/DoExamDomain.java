package th.ac.kbu.cs.ExamProject.Domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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
public class DoExamDomain extends DoExamPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;

	@Autowired
	private DoExamService doExamService;
	
	private Date expireDate;
	
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public List<HashMap<String,Object>> doExam(){
		validateParameter();
		ExamResult examResult = this.getExamResult(getExamResultId());
		validateDoExamExpired(examResult);
		List<HashMap<String,Object>> results =  getQuestionAnswerData(examResult);
		this.setExpireDate(examResult.getExamExpireDate());
		this.setNumOfQuestion(results.size());
		
		return results;
	}
	
	private void validateParameter(){
		if(BeanUtils.isEmpty(this.getExamResultId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	private ExamResult getExamResult(Long examResultId){
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
	
	private void validateDoExamExpired(ExamResult examResult){
		if(BeanUtils.isNotEmpty(examResult.getExamExpireDate())){
			Date nowToday = new Date();
			if(nowToday.after(examResult.getExamExpireDate())){
				throw new CoreException(CoreExceptionMessage.EXAM_EXPIRED);
			}
		}
	}

	private void validateSaveExamExpired(ExamResult examResult){
		if(BeanUtils.isNotEmpty(examResult.getExamExpireDate())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(examResult.getExamExpireDate());
			cal.add(Calendar.MINUTE, 3);
			Date nowToday = new Date();
			if(nowToday.after(cal.getTime())){
				throw new CoreException(CoreExceptionMessage.EXAM_EXPIRED);
			}
		}
	}
	private List<HashMap<String,Object>> getQuestionAnswerData(ExamResult examResult){
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResultAnswer.class,"examResultAnswer");
		criteria.createAlias("examResultAnswer.question", "question");
		criteria.createAlias("examResultAnswer.answer1", "answer1");
		criteria.createAlias("examResultAnswer.answer2", "answer2");
		criteria.createAlias("examResultAnswer.answer3", "answer3");
		criteria.createAlias("examResultAnswer.answer4", "answer4");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("question.questionId"),"questionId");
		projectionList.add(Projections.property("question.questionText"),"questionText");
		projectionList.add(Projections.property("answer1.answerId"),"answer1Id");
		projectionList.add(Projections.property("answer1.answerText"),"answer1Text");
		projectionList.add(Projections.property("answer2.answerId"),"answer2Id");
		projectionList.add(Projections.property("answer2.answerText"),"answer2Text");
		projectionList.add(Projections.property("answer3.answerId"),"answer3Id");
		projectionList.add(Projections.property("answer3.answerText"),"answer3Text");
		projectionList.add(Projections.property("answer4.answerId"),"answer4Id");
		projectionList.add(Projections.property("answer4.answerText"),"answer4Text");
		projectionList.add(Projections.property("examResultAnswer.examResultAnswerId"), "examResultAnswerId");
		projectionList.add(Projections.property("examResultAnswer.answerId"), "answerId");
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("examResultAnswer.examResultId", examResult.getExamResultId()));
		criteria.addOrder(Order.asc("examResultAnswer.ordinal"));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
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
	public void validateAnswerData(List<ExamResultAnswer> examResultAnswers,Long examResultId){
		for(ExamResultAnswer examResultAnswer : examResultAnswers){
			if(examResultAnswer.getExamResultId() != examResultId){
				throw new CoreException(CoreExceptionMessage.INVALID_DATA);
			}
		}
	}


	public void validateExamResult() {
		validateParameter();
		ExamResult examResult = this.getExamResult(getExamResultId());
		validateDoExamExpired(examResult);
	}
	
}
