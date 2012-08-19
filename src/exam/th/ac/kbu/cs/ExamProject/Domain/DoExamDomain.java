package th.ac.kbu.cs.ExamProject.Domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.Answer;
import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamQuestionGroup;
import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.Question;
import th.ac.kbu.cs.ExamProject.Exception.CantExamEnoughException;
import th.ac.kbu.cs.ExamProject.Exception.ExamIsExpiredException;
import th.ac.kbu.cs.ExamProject.Exception.ExamNotStartedException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Exception.QuestionNotEnoughException;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.DoExamService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

/**
 * Processing
 * -> check permission to examing
 * -> cal numOfQuestion
 * -> check numOfQuestion and requestQuestion
 * -> query question
 * -> insert to examResult
 * -> query answer
 * -> return
 * @author Core
 */
@Configurable
public class DoExamDomain extends DoExamPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private DoExamService doExamService;
	
	private Integer timeLimit;
	
	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public void checkCanDoQuestion(){
		if(BeanUtils.isEmpty(this.getExamId()) 
				|| BeanUtils.isEmpty(this.getNumOfQuestion())
				|| BeanUtils.isEmpty(this.getExamCount())){
			throw new ParameterNotFoundException("Parameter not found!!");
		}
		Date nowToday = new Date();
		Exam exam = this.getExam(this.getExamId());
		if(BeanUtils.isNotNull(exam.getStartDate()) && exam.getStartDate().before(nowToday)){
			throw new ExamNotStartedException("Exam not started!!");
		}
		if(BeanUtils.isNotNull(exam.getEndDate()) && exam.getEndDate().after(nowToday)){
			throw new ExamIsExpiredException("Exam is expired!!");
		}
		if(BeanUtils.isNotNull(exam.getExamLimit()) && exam.getExamLimit() <= this.getExamCount(this.getExamId())){
			throw new CantExamEnoughException("Cant exam anymore!!");
		}
		
	}
	
	private Integer getExamCount(Long examId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResult.class,"examResult");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount(),"examCount");
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("examResult.examId", examId));
		criteria.add(Restrictions.eq("examResult.username",SecurityUtils.getUsername()));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		HashMap<String, Object> result = basicFinderService.findUniqueByCriteria(criteria);
		
		return BeanUtils.toInteger(result.get("examCount"));
	}
	
	private Exam getExam(Long examId){
		DetachedCriteria criteria = DetachedCriteria.forClass(Exam.class,"exam");
		criteria.add(Restrictions.eq("exam.examId", examId));
		
		return basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public List<HashMap<String, Object>> createQuestion() {
		checkCanDoQuestion();
		HashMap<Long,HashMap<String,Object>> numOfQuestion = calNumOfQuestion(this.getExamId(),this.getNumOfQuestion());
		checkNumOfQuestion(numOfQuestion);
		this.setTimeLimit(calTotalTime(numOfQuestion));
		List<HashMap<String, Object>> questionData = queryQuestion(numOfQuestion);
		insertExamResult(questionData,this.getTimeLimit(),this.getNumOfQuestion());
		queryAnswer(questionData);
		return questionData;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private void printData(List<HashMap<String, Object>> questionData){
		for(HashMap<String, Object> question : questionData){
			System.out.println("---------------");
			System.out.println("questionId :"+question.get("questionId"));
			System.out.println("questionText :"+question.get("questionText"));
			List<HashMap<String, Object>> answerData = (List<HashMap<String, Object>>) question.get("answerData");
			for(HashMap<String, Object> answer : answerData){
				System.out.println("answerId :"+answer.get("answerId"));
				System.out.println("answerText :"+answer.get("answerText"));
			}
			System.out.println("---------------");
		}
	}

	private void insertExamResult(List<HashMap<String, Object>> questionData, Integer totalTime,Integer totalNumOfQuestion) {
		ExamResult examResult = new ExamResult();
		examResult.setExamId(this.getExamId());
		examResult.setUsername(SecurityUtils.getUsername());
		examResult.setNumOfQuestion(totalNumOfQuestion);
		examResult.setExamCount(this.getExamCount());
		examResult.setExamStartDate(new Date());
		examResult.setExamLimitTime(totalTime);
		examResult.setExamCompleted(false);
		doExamService.createExamResult(examResult, questionData);
	}

	private Integer calTotalTime(HashMap<Long, HashMap<String, Object>> numOfQuestion) {
		Integer total = 0;
		for(Long questionGroupId : numOfQuestion.keySet()){
			total+= (BeanUtils.toInteger(numOfQuestion.get(questionGroupId).get("secondPerQuestion")) 
					* BeanUtils.toInteger(numOfQuestion.get(questionGroupId).get("numOfQuestion")));	
		}
		return total;
	}

	/**
	 *  if requestQuestion > numOfQuestion throws QuestionNotEnoughException()
	 */
	public void checkNumOfQuestion(HashMap<Long,HashMap<String,Object>> numOfQuestion){
		DetachedCriteria criteria = DetachedCriteria.forClass(Question.class,"question");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("question.questionGroupId"),"questionGroupId");
		projectionList.add(Projections.count("question.questionId"),"numOfQuestion");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.in("question.questionGroupId", numOfQuestion.keySet()));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		List<HashMap<String,Object>> results = basicFinderService.findByCriteria(criteria);
		

		for(HashMap<String,Object> result : results){
			Long questionGroupId = BeanUtils.toLong(result.get("questionGroupId"));
			Integer nOfQuestion = BeanUtils.toInteger(result.get("numOfQuestion"));
			Integer requestNumOfQuestion = BeanUtils.toInteger(numOfQuestion.get(questionGroupId).get("numOfQuestion"));
//			System.out.println("-----------------");
//			System.out.println("QuestionGroupId : "+questionGroupId);
//			System.out.println("numOfQuestion : "+nOfQuestion);
//			System.out.println("RequestNumOfQuestion : "+requestNumOfQuestion);
//			System.out.println("-----------------");
			if(nOfQuestion < requestNumOfQuestion){
				throw new QuestionNotEnoughException("Question Not Enough");
			}
		}
	}
	
	/**
	 * key = questionGroupId 
	 * value = numOfQuestion
	 */
	public HashMap<Long,HashMap<String,Object>> calNumOfQuestion(Long examId,Integer nOfQ) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamQuestionGroup.class,"examQuestionGroup");
		
		criteria.add(Restrictions.eq("examQuestionGroup.examId", examId));
		
		List<ExamQuestionGroup> results = basicFinderService.findByCriteria(criteria);
		
		
		Integer total = 0;
		
		HashMap<Long,HashMap<String,Object>> numOfQuestion = new HashMap<Long,HashMap<String,Object>>();
		
		HashMap<Long,Float> fragment = new HashMap<Long,Float>();
		
		for(ExamQuestionGroup result : results){
			HashMap<String,Object> examQuestionGroup = new HashMap<String,Object>();
			
			BigDecimal nOfQuestion = BeanUtils.toBigDecimal(
					( result.getQuestionPercent().floatValue() / 100)* nOfQ )
					.setScale(2, RoundingMode.HALF_UP);
			
			examQuestionGroup.put("numOfQuestion", nOfQuestion.setScale(0, RoundingMode.FLOOR).intValue());
			examQuestionGroup.put("secondPerQuestion", result.getSecondPerQuestion());
			
			fragment.put(result.getQuestionGroupId()
					,BeanUtils.toFloat(nOfQuestion.floatValue() % 100));
			
			total+= nOfQuestion.setScale(0, RoundingMode.FLOOR).intValue();

			numOfQuestion.put(result.getQuestionGroupId(), examQuestionGroup);
		}
		
		updateNumOfQuestion(numOfQuestion,fragment,nOfQ - total);
		

		return numOfQuestion;
	}
	
	public void updateNumOfQuestion(HashMap<Long,HashMap<String,Object>> numOfQuestion,HashMap<Long,Float> fragment,Integer size) {
		
		Long maxKey = null;
		for(int i=1;i<=size;i++){
			for(Long key : fragment.keySet()){
				if(BeanUtils.isNull(maxKey)){
					maxKey = key;
				}
				if (BeanUtils.toInteger(numOfQuestion.get(key).get("numOfQuestion")) == 0){
					maxKey = key;
					break;
				}else if(fragment.get(key) > fragment.get(maxKey)){
					maxKey = key;
				}
			}
			Integer nOfQ = BeanUtils.toInteger(numOfQuestion.get(maxKey).get("numOfQuestion"));
			nOfQ++;
			numOfQuestion.get(maxKey).put("numOfQuestion",nOfQ);
			fragment.remove(maxKey);
			maxKey = null;
		}
	}
	
	public List<HashMap<String, Object>> queryQuestion(HashMap<Long,HashMap<String,Object>> numOfQuestion){
		List<HashMap<String, Object>> records = new ArrayList<HashMap<String,Object>>();
		for(Long questionGroupId : numOfQuestion.keySet()){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(Question.class,"question");
			
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.property("question.questionId"),"questionId");
			projectionList.add(Projections.property("question.questionText"),"questionText");
			
			subCriteria.setProjection(projectionList);

			subCriteria.add(Restrictions.eq("question.flag", true));
			subCriteria.add(Restrictions.eq("question.questionGroupId", questionGroupId));
			subCriteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));

			subCriteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			List<HashMap<String, Object>> results = basicFinderService.findByCriteria(subCriteria, 0, BeanUtils.toInteger(numOfQuestion.get(questionGroupId).get("numOfQuestion")));
			records.addAll(results);
		}
		return records;
	}
	
	public void queryAnswer(List<HashMap<String, Object>> questionData){
		for( HashMap<String, Object> question : questionData){
			DetachedCriteria foolCriteria = DetachedCriteria.forClass(Answer.class,"answer");
			
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.property("answer.answerId"),"answerId");
			projectionList.add(Projections.property("answer.answerText"),"answerText");
			
			foolCriteria.setProjection(projectionList);
			foolCriteria.add(Restrictions.eq("answer.questionId", question.get("questionId")));
			foolCriteria.add(Restrictions.eq("answer.flag", true));
			foolCriteria.add(Restrictions.eq("answer.answerScore",0));
			foolCriteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
			foolCriteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<HashMap<String, Object>> answerList = basicFinderService.findByCriteria(foolCriteria,0,3);
			
			DetachedCriteria correctCriteria = DetachedCriteria.forClass(Answer.class,"answer");
			
			correctCriteria.setProjection(projectionList);
			correctCriteria.add(Restrictions.eq("answer.questionId", question.get("questionId")));

			correctCriteria.add(Restrictions.eq("answer.flag", true));
			correctCriteria.add(Restrictions.eq("answer.answerScore",1));
			correctCriteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			correctCriteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
			HashMap<String, Object> correctAnswer = basicFinderService.findUniqueByCriteria(correctCriteria);
			
			answerList.add(correctAnswer);
			
			question.put("answerData", answerList);
		}
	}
 	
}
