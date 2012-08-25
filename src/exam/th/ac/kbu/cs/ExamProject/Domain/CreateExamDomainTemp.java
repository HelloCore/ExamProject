package th.ac.kbu.cs.ExamProject.Domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.orm.hibernate3.HibernateCallback;

import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Exception.CantExamEnoughException;
import th.ac.kbu.cs.ExamProject.Exception.ExamIsExpiredException;
import th.ac.kbu.cs.ExamProject.Exception.ExamNotStartedException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Exception.QuestionNotEnoughException;
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
public class CreateExamDomainTemp extends DoExamPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	@Autowired
	private DoExamService doExamService;
	
	public Long createExamResultHql() throws ParseException{

		Calendar nowToday = Calendar.getInstance();
		Exam exam = getAndCheckExam(nowToday.getTime());
		
		HashMap<Long,Integer> requestNumOfQuestion = getAndCheckRequestNumOfQuestion(this.getExamId(),this.getNumOfQuestion());
		Date expireDate = this.calExapireDateNew(nowToday, exam.getEndDate());
		
		List<Object[]> questionData = queryQuestion(requestNumOfQuestion);
		if(!exam.getExamSequence()){
			Collections.shuffle(questionData);
		}
		
		return insertExamResultObj(questionData,expireDate,this.getNumOfQuestion());
	}
	
	/**
	 * validate Data
	 * @throws ParseException 
	 */
	public Exam getAndCheckExam(Date nowToday) throws ParseException{
		if(BeanUtils.isEmpty(this.getExamId()) 
				|| BeanUtils.isEmpty(this.getNumOfQuestion())
				|| BeanUtils.isEmpty(this.getExamCount())){
			throw new ParameterNotFoundException("Parameter not found!!");
		}
		
		Exam exam = this.getExam(this.getExamId());
		if(BeanUtils.isNotNull(exam.getStartDate()) && exam.getStartDate().before(nowToday)){
			throw new ExamNotStartedException("Exam not started!!");
		}
		if(BeanUtils.isNotNull(exam.getEndDate()) && exam.getEndDate().before(nowToday)){
			throw new ExamIsExpiredException("Exam is expired!!");
		}
		if(BeanUtils.isNotNull(exam.getExamLimit()) && exam.getExamLimit() <= exam.getExamCount()){
			throw new CantExamEnoughException("Cant exam anymore!!");
		}
		return exam;
	}
	
	
	private Exam getExam(Long examId) throws ParseException{
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
					.append(" exam.examId ")
					.append(" ,exam.examHeader ")
					.append(" ,exam.startDate ")
					.append(" ,exam.endDate ")
					.append(" ,exam.examLimit ")
					.append(" ,exam.minQuestion ")
					.append(" ,exam.maxQuestion ")
					.append(" ,exam.courseId ")
					.append(" ,exam.examSequence ")
					.append(" ,(SELECT COUNT(examResult.examResultId) ")
						.append(" FROM ExamResult examResult ")
						.append(" WHERE examResult.examId = exam.examId ")
						.append(" AND examResult.username = ?")
					.append(" ) ")
					.append(" FROM ExamSection examSection ")
					.append(" JOIN examSection.exam exam")
					.append(" WHERE exam.examId = ? ")
						.append(" AND examSection.sectionId in ( ")
							.append(" SELECT studentSection.sectionId ")
							.append(" FROM StudentSection studentSection ")
							.append(" WHERE studentSection.username = ? ")
						.append(" )")
					.append(" GROUP BY exam.examId ");
		final String username = SecurityUtils.getUsername();
		Exam exam = new Exam();
		Object[] examObj = basicFinderService.findUnique(queryString.toString(), new Object[]{
			username,examId,username
		});
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		exam.setExamId(BeanUtils.toLong(examObj[0]));
		exam.setExamHeader(examObj[1].toString());
		if(BeanUtils.isNotNull(examObj[2])){
			exam.setStartDate(sdf.parse(examObj[2].toString()));
		}
		if(BeanUtils.isNotNull(examObj[3])){
			exam.setEndDate(sdf.parse(examObj[3].toString()));
		}
		exam.setExamLimit(BeanUtils.toInteger(examObj[4]));
		exam.setMinQuestion(BeanUtils.toInteger(examObj[5]));
		exam.setMaxQuestion(BeanUtils.toInteger(examObj[6]));
		exam.setCourseId(BeanUtils.toLong(examObj[7]));
		exam.setExamSequence(BeanUtils.toBoolean(examObj[8]));
		exam.setExamCount(BeanUtils.toInteger(examObj[9]));
		return exam;
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

	@SuppressWarnings("unchecked")
	public List<Object[]> queryQuestion(HashMap<Long,Integer> requestNumOfQuestion){

		List<Object[]> records = new ArrayList<Object[]>();
		for(final Long questionGroupId : requestNumOfQuestion.keySet()){
			final Integer limit = BeanUtils.toInteger(requestNumOfQuestion.get(questionGroupId));
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
 	
	public HashMap<Long,Integer> getAndCheckRequestNumOfQuestion(Long examId,Integer numOfQuestion){
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
			.append(" GROUP BY examQuestionGroup.questionGroupId ")
			.append(" ORDER BY examQuestionGroup.ordinal ASC ");
		List<Object[]> results = basicFinderService.find(sb.toString(), examId);
		
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
		Integer timeLimitSec = 0;
		for(Long questionGroupId : requestNumOfQuestion.keySet()){
			Integer requestQuestion = requestNumOfQuestion.get(questionGroupId);
			Integer correctQuestion = correctNumOfQuestion.get(questionGroupId);
			if(correctQuestion < requestQuestion){
				throw new QuestionNotEnoughException("Question Not Enough");
			}else{
				timeLimitSec += (requestQuestion * secondPerQuestion.get(questionGroupId));
			}
		}
		this.setTimeLimitSec(timeLimitSec);
		return requestNumOfQuestion;
	}
	
	private Date calExapireDateNew(Calendar nowToday,Date endDate) {
		Date expireDate;
		nowToday.add(Calendar.SECOND, this.getTimeLimitSec());
		if(BeanUtils.isNotEmpty(endDate) && endDate.before(nowToday.getTime())){
			expireDate = endDate;	
		}else{
			expireDate = nowToday.getTime();
		}
		return expireDate;
	}
	
	private Integer timeLimitSec;

	public Integer getTimeLimitSec() {
		return timeLimitSec;
	}

	public void setTimeLimitSec(Integer timeLimitSec) {
		this.timeLimitSec = timeLimitSec;
	}
	
}
