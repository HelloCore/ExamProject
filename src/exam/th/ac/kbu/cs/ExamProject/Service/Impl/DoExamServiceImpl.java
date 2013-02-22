package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.ExamResultAnswer;
import th.ac.kbu.cs.ExamProject.Entity.ExamTempTable;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.DoExamService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Service
public class DoExamServiceImpl implements DoExamService{

	@Autowired
	private BasicEntityService basicEntityService;
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	public Serializable saveExamResult(ExamResult examResult) {
		System.out.println("saveExamResult");
		return basicEntityService.save(examResult);
	}

	@Transactional(rollbackFor=Exception.class)
	public Long createExamResult(ExamResult examResult,List<HashMap<String, Object>> questionData) {
		System.out.println("createExamResult");
		Long examResultId = BeanUtils.toLong(this.saveExamResult(examResult));
		Long examResultAnswerId ;
		List<ExamResultAnswer> examResultAnswerList = new ArrayList<ExamResultAnswer>();
		
		Integer ordinal = 0;
		for(HashMap<String, Object> question : questionData){
			ExamResultAnswer examResultAnswer = this.toExamResultAnswer(examResultId, question);
			examResultAnswer.setOrdinal(ordinal);
			examResultAnswerId = BeanUtils.toLong(basicEntityService.save(examResultAnswer));
			examResultAnswer.setExamResultAnswerId(examResultAnswerId);
			examResultAnswerList.add(examResultAnswer);
			ordinal++;
		}
		return examResultId;
	}

	@SuppressWarnings("unchecked")
	private ExamResultAnswer toExamResultAnswer(Long examResultId,HashMap<String,Object> question){
		System.out.println("toExamResultAnswer");
		ExamResultAnswer examResultAnswer = new ExamResultAnswer();
		examResultAnswer.setExamResultId(examResultId);
		examResultAnswer.setQuestionId(BeanUtils.toLong(question.get("questionId")));
		
		
		List<HashMap<String, Object>> answerData = (List<HashMap<String, Object>>) question.get("answerData");
		List<Long> answerList = new ArrayList<Long>();
		for(HashMap<String, Object> answer : answerData){
			answerList.add(BeanUtils.toLong(answer.get("answerId")));
		}
		examResultAnswer.setAnswerId1(answerList.get(0));
		examResultAnswer.setAnswerId2(answerList.get(1));
		examResultAnswer.setAnswerId3(answerList.get(2));
		examResultAnswer.setAnswerId4(answerList.get(3));
		return examResultAnswer;
	}
	
	public void updateExamResultAnswer(ExamResultAnswer examResultAnswer) {
		System.out.println("updateExamResultAnswer");
		basicEntityService.update(examResultAnswer);
	}

	@Transactional(rollbackFor=Exception.class)
	public void updateExamResult(ExamResult examResult,
			List<ExamResultAnswer> examResultAnswers,
			Boolean isExpired,
			Boolean isAutoSave) {
		System.out.println("updateExamResult");
		if(!isExpired){
//			String queryString = "UPDATE ExamResultAnswer examResultAnswer SET examResultAnswer.answerId=null WHERE examResultAnswer.examResultId = ?";
//			basicEntityService.bulkUpdate(queryString, examResult.getExamResultId());
//		}else{
			if(BeanUtils.isNotEmpty(examResultAnswers)){
				basicEntityService.update(examResultAnswers);
			}
		}
		if(!isAutoSave){
			calScore(examResult,isExpired);
			basicEntityService.update(examResult);
		}
	}

	public void insertExamTempTable(List<ExamTempTable> examTempTableList) {
		System.out.println("saveExamResult");
		basicEntityService.save(examTempTableList);
	}

	@Transactional(rollbackFor=Exception.class)
	public void setCompleteExamFromSelect(ExamResult examResult,Boolean isExpired){	
		System.out.println("setCompleteExamFromSelect");
//		if(isExpired){
//			String queryString = "UPDATE ExamResultAnswer examResultAnswer SET examResultAnswer.answerId=null WHERE examResultAnswer.examResultId = ?";
//			basicEntityService.bulkUpdate(queryString, examResult.getExamResultId());
//		}
		calScore(examResult,isExpired);
		basicEntityService.update(examResult);
	}
	
	public <T> T execute(HibernateCallback<T> action){
		System.out.println("execute");
		return basicEntityService.execute(action);
	}

	@Transactional(rollbackFor=Exception.class)
	public Long createExamResultObj(ExamResult examResult,
			List<Object[]> questionData) {
		System.out.println("createExamResultObj");
		Long examResultId = BeanUtils.toLong(this.saveExamResult(examResult));
		Long examResultAnswerId ;
		List<ExamResultAnswer> examResultAnswerList = new ArrayList<ExamResultAnswer>();
		
		Integer ordinal = 0;
		for(Object[] questionAnswer : questionData){
			ExamResultAnswer examResultAnswer = this.toExamResultAnswerObj(examResultId, questionAnswer);
			examResultAnswer.setOrdinal(ordinal);
			examResultAnswerId = BeanUtils.toLong(basicEntityService.save(examResultAnswer));
			examResultAnswer.setExamResultAnswerId(examResultAnswerId);
			examResultAnswerList.add(examResultAnswer);
			ordinal++;
		}
		return examResultId;
	}
	private Exam getExamData(Long examId){
		System.out.println("getExamData");
		DetachedCriteria criteria = DetachedCriteria.forClass(Exam.class,"exam");
		criteria.add(Restrictions.eq("exam.examId",examId));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
	private void calScore(ExamResult examResult,Boolean isExpired){
		System.out.println("calScore");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResultAnswer.class,"examResultAnswer");
		criteria.createAlias("examResultAnswer.answer","answer");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount());
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("examResultAnswer.examResultId", examResult.getExamResultId()));
		criteria.add(Restrictions.ge("answer.answerScore", 1));
		Integer correctAnswer = BeanUtils.toInteger(basicFinderService.findUniqueByCriteria(criteria));
	
		examResult.setExamCorrect(correctAnswer);

		if(isExpired){
			examResult.setExamScore(0F);
		}else{
			Exam exam = this.getExamData(examResult.getExamId());
			
			Float numOfQuestion = examResult.getNumOfQuestion().floatValue();
			Float maxScore = exam.getMaxScore().floatValue();
			Float score = (correctAnswer.floatValue() / numOfQuestion) * maxScore;
			
			examResult.setExamScore(score);
		}
		
	}
	
	private ExamResultAnswer toExamResultAnswerObj(Long examResultId,Object[] questionData){
		System.out.println("toExamResultAnswerObj");
		ExamResultAnswer examResultAnswer = new ExamResultAnswer();
		examResultAnswer.setExamResultId(examResultId);
		examResultAnswer.setQuestionId(BeanUtils.toLong(questionData[0]));
		List<Long> answerList = new ArrayList<Long>();
		answerList.add(BeanUtils.toLong(questionData[1]));
		answerList.add(BeanUtils.toLong(questionData[2]));
		answerList.add(BeanUtils.toLong(questionData[3]));
		answerList.add(BeanUtils.toLong(questionData[4]));
		Collections.shuffle(answerList);
		examResultAnswer.setAnswerId1(answerList.get(0));
		examResultAnswer.setAnswerId2(answerList.get(1));
		examResultAnswer.setAnswerId3(answerList.get(2));
		examResultAnswer.setAnswerId4(answerList.get(3));
		return examResultAnswer;
	}
}
