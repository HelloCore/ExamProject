package th.ac.kbu.cs.ExamProject.Domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.orm.hibernate3.HibernateCallback;

import th.ac.kbu.cs.ExamProject.Entity.Answer;
import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamQuestionGroup;
import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.ExamResultAnswer;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
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
 * -> query answer
 * -> insert to examResult and examResultAnswer
 * @author Core
 */
@Configurable
public class CreateExamDomain extends DoExamPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	@Autowired
	private DoExamService doExamService;
	
	public Long createExamResult() {
		// validate data
		Calendar nowToday = Calendar.getInstance();
		Exam exam = getAndCheckExam(nowToday.getTime());
		HashMap<Long,HashMap<String,Object>> numOfQuestion = calNumOfQuestion(this.getExamId(),this.getNumOfQuestion());
		checkNumOfQuestion(numOfQuestion);
		
		Date expireDate = this.calExapireDate(numOfQuestion, nowToday, exam.getEndDate());
		
		//query insert data
		List<Object[]> questionData = queryQuestion(numOfQuestion);
		if(!exam.getExamSequence()){
			Collections.shuffle(questionData);
		}
		
		return insertExamResultObj(questionData,expireDate,this.getNumOfQuestion());
	}

	/**
	 * validate Data
	 */
	public Exam getAndCheckExam(Date nowToday){
		if(BeanUtils.isEmpty(this.getExamId()) 
				|| BeanUtils.isEmpty(this.getNumOfQuestion())
				|| BeanUtils.isEmpty(this.getExamCount())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
		
		Exam exam = this.getExam(this.getExamId());
		if(BeanUtils.isNotNull(exam.getStartDate()) && exam.getStartDate().before(nowToday)){
			throw new CoreException(CoreExceptionMessage.EXAM_NOT_STARTED);
		}
		if(BeanUtils.isNotNull(exam.getEndDate()) && exam.getEndDate().before(nowToday)){
			throw new CoreException(CoreExceptionMessage.EXAM_EXPIRED);
		}
		if(BeanUtils.isNotNull(exam.getExamLimit()) && exam.getExamLimit() <= this.getExamCount(this.getExamId())){
			throw new CoreException(CoreExceptionMessage.CANT_DO_EXAM_ANYMORE);
		}
		return exam;
	}
	/**
	 * cal numOfQuestion
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
	
	private Long insertExamResultObj(List<Object[]> questionData, Date expireDate,Integer totalNumOfQuestion) {
		Calendar cal = Calendar.getInstance();
		
		ExamResult examResult = new ExamResult();
		examResult.setExamId(this.getExamId());
		examResult.setUsername(SecurityUtils.getUsername());
		examResult.setNumOfQuestion(totalNumOfQuestion);
		examResult.setExamCount(this.getExamCount());
		examResult.setExamStartDate(cal.getTime());
		examResult.setExamExpireDate(expireDate);
		examResult.setExamCompleted(false);
		return doExamService.createExamResultObj(examResult, questionData);
	}

	private Long insertExamResult(List<HashMap<String, Object>> questionData, Date expireDate,Integer totalNumOfQuestion) {
		Calendar cal = Calendar.getInstance();
		
		ExamResult examResult = new ExamResult();
		examResult.setExamId(this.getExamId());
		examResult.setUsername(SecurityUtils.getUsername());
		examResult.setNumOfQuestion(totalNumOfQuestion);
		examResult.setExamCount(this.getExamCount());
		examResult.setExamStartDate(cal.getTime());
		examResult.setExamExpireDate(expireDate);
		examResult.setExamCompleted(false);
		return doExamService.createExamResult(examResult, questionData);
	}
	
	private Date calExapireDate(HashMap<Long, HashMap<String, Object>> numOfQuestion,Calendar nowToday,Date endDate) {
		Integer total = 0;
		Date expireDate;
		for(Long questionGroupId : numOfQuestion.keySet()){
			total+= (BeanUtils.toInteger(numOfQuestion.get(questionGroupId).get("secondPerQuestion")) 
					* BeanUtils.toInteger(numOfQuestion.get(questionGroupId).get("numOfQuestion")));	
		}
		nowToday.add(Calendar.SECOND, total);
		if(BeanUtils.isNotEmpty(endDate) && endDate.before(nowToday.getTime())){
			expireDate = endDate;	
		}else{
			expireDate = nowToday.getTime();
		}
		return expireDate;
	}

	/**
	 *  if requestQuestion > numOfQuestion throws QuestionNotEnoughException()
	 */
	public void checkNumOfQuestion(HashMap<Long,HashMap<String,Object>> numOfQuestion){
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT question.questionGroupId,COUNT(question.questionId) ")
			.append(" FROM Question question ")
			.append(" WHERE question.flag=(:flag) ")
				.append(" AND question.questionGroupId in (:questionGroupId) ")
				.append(" Group By question.questionGroupId ");
		List<Object[]> results = basicFinderService.findByNamedParam(sb.toString(),new String[]{ 
			"flag","questionGroupId"
		},new Object[]{
			true,
			numOfQuestion.keySet()
		});
		
		
		for(Object[] result : results){
			Long questionGroupId = BeanUtils.toLong(result[0]);
			Integer nOfQuestion = BeanUtils.toInteger(result[1]);
			Integer requestNumOfQuestion = BeanUtils.toInteger(numOfQuestion.get(questionGroupId).get("numOfQuestion"));
			if(nOfQuestion < requestNumOfQuestion){
				throw new CoreException(CoreExceptionMessage.QUESTION_NOT_ENOUGH);
			}
		}
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
	
	@SuppressWarnings("unchecked")
	public List<Object[]> queryQuestion(HashMap<Long,HashMap<String,Object>> numOfQuestion){
		
		List<Object[]> records = new ArrayList<Object[]>();
		for(final Long questionGroupId : numOfQuestion.keySet()){
			final Integer limit = BeanUtils.toInteger(numOfQuestion.get(questionGroupId).get("numOfQuestion"));
			StringBuilder queryString = new StringBuilder();
			queryString.append(" SELECT ").append("\n")
					.append(" question.questionId ").append("\n")
					.append(" ,answer1.answerId ").append("\n")
					.append(" ,answer2.answerId ").append("\n")
					.append(" ,answer3.answerId ").append("\n")
					.append(" ,answer4.answerId ").append("\n")
				.append(" FROM Question question ").append("\n")
					.append(" , Answer answer1 ")
					.append(" ,Answer answer2 ")
					.append(" ,Answer answer3 ")
					.append(" ,Answer answer4 ")
				.append(" WHERE question.questionGroupId = ? ").append("\n")
					.append(" AND answer1.questionId = question.questionId ").append("\n")
					.append(" AND answer1.answerScore = 1 AND answer1.flag = 1 ").append("\n")
					.append(" AND answer2.questionId = question.questionId ").append("\n")
					.append(" AND answer2.answerScore = 0 AND answer2.flag = 1 ").append("\n")
					.append(" AND answer3.questionId = question.questionId ").append("\n")
					.append(" AND answer3.answerId <> answer2.answerId ")
					.append(" AND answer3.answerScore = 0 AND answer2.flag = 1 ").append("\n")
					.append(" AND answer4.questionId = question.questionId ").append("\n")
					.append(" AND answer4.answerScore = 0 AND answer2.flag = 1 ").append("\n")
					.append(" AND answer4.answerId <> answer3.answerId ")
					.append(" AND answer4.answerId <> answer2.answerId ")
				.append(" GROUP BY question.questionId ").append("\n")
				.append(" ORDER BY RAND()");
				
			final String fQueryString = queryString.toString(); 
			List result = (List)basicEntityService.execute(new HibernateCallback<Object>() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createQuery(fQueryString).setParameter(0, questionGroupId).setMaxResults(limit).list();
				}
			});
			records.addAll(result);
		}
		return records;
 	}
	
	public void queryAnswer(List<HashMap<String, Object>> questionData){
		
		for( HashMap<String, Object> question : questionData){
			DetachedCriteria foolCriteria = DetachedCriteria.forClass(Answer.class,"answer");
			
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.property("answer.answerId"),"answerId");
			
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
 	
	public void forceUpdate(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResultAnswer.class,"examResultAnswer");
		criteria.add(Restrictions.eq("examResultAnswer.examResultAnswerId", BeanUtils.toLong(100)));
		ExamResultAnswer examResultAnswer = basicFinderService.findUniqueByCriteria(criteria);
		examResultAnswer.setQuestionId(BeanUtils.toLong(100));
		examResultAnswer.setAnswerId(BeanUtils.toLong(100));
		
		doExamService.updateExamResultAnswer(examResultAnswer);
	}
	
	public List<Object[]> getAndCheckNumOfQuestion(Long examId,Integer numOfQuestion){
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ")
			.append(" examQuestionGroup.questionGroupId ")
			.append(" ,examQuestionGroup.questionPercent ")
			.append(" ,examQuestionGroup.secondPerQuestion ")
			.append(" ,COUNT(question.questionId) ")
			.append(" FROM ExamQuestionGroup examQuestionGroup ")
			.append(" ,Question question ")
			.append(" WHERE question.questionGroupId = examQuestionGroup.questionGroupId ")
			.append(" AND question.flag = 1 ")
			.append(" AND examQuestionGroup.examId = ? ")
			.append(" GROUP BY question.questionId ")
			.append(" ORDER BY examQuestionGroup.ordinal ASC ");
		List<Object[]> results = basicFinderService.find(sb.toString(), examId);
		
		HashMap<Long,Integer> questionPercent = new HashMap<Long,Integer>();
		HashMap<Long,Integer> secondPerQuestion = new HashMap<Long,Integer>();
		HashMap<Long,Integer> correctNumOfQuestion = new HashMap<Long,Integer>();
		HashMap<Long,Integer> requestNumOfQuestion = new HashMap<Long,Integer>();
		HashMap<Long,Float> fragment = new HashMap<Long,Float>();
		
		//loop assign data
		Integer totalQuestion = 0;
		
		for(Object[] result : results){
			BigDecimal nOfQuestion = BeanUtils.toBigDecimal(
				( BeanUtils.toInteger(result[1]).floatValue() / 100)* numOfQuestion )
				.setScale(2, RoundingMode.HALF_UP);
			Long questionGroupId = BeanUtils.toLong(result[0]);
			requestNumOfQuestion.put(questionGroupId, nOfQuestion.setScale(0, RoundingMode.FLOOR).intValue());
			questionPercent.put(questionGroupId, BeanUtils.toInteger(result[1]));
			secondPerQuestion.put(questionGroupId, BeanUtils.toInteger(result[2]));
			correctNumOfQuestion.put(questionGroupId, BeanUtils.toInteger(result[3]));
			fragment.put(questionGroupId,BeanUtils.toFloat(nOfQuestion.floatValue() % 100));
			
			totalQuestion+= nOfQuestion.setScale(0, RoundingMode.FLOOR).intValue();
		}
		Integer size = numOfQuestion - totalQuestion;
		Long maxKey = null;
		for(int i=1;i<=size;i++){
			for(Long key : fragment.keySet()){
				if(BeanUtils.isNull(maxKey)){
					maxKey = key;
				}
				if (BeanUtils.toInteger(requestNumOfQuestion.get(key)) == 0){
					maxKey = key;
					break;
				}else if(fragment.get(key) > fragment.get(maxKey)){
					maxKey = key;
				}
			}
			Integer nOfQ = BeanUtils.toInteger(requestNumOfQuestion.get(maxKey));
			nOfQ++;
			requestNumOfQuestion.put(maxKey,nOfQ);
			fragment.remove(maxKey);
			maxKey = null;
		}
		
		for(Long questionGroupId : requestNumOfQuestion.keySet()){
			Integer requestQuestion = requestNumOfQuestion.get(questionGroupId);
			Integer correctQuestion = correctNumOfQuestion.get(questionGroupId);
			if(correctQuestion < requestQuestion){
				throw new CoreException(CoreExceptionMessage.QUESTION_NOT_ENOUGH);
			}
		}
		
		return null;
	}
}
