package th.ac.kbu.cs.ExamProject.Domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.Answer;
import th.ac.kbu.cs.ExamProject.Entity.ExamQuestionGroup;
import th.ac.kbu.cs.ExamProject.Entity.Question;
import th.ac.kbu.cs.ExamProject.Exception.QuestionNotEnoughException;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

/**
 * @author Core
 *
 */
@Configurable
public class DoExamDomain extends DoExamPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	public void selectAlwayError(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Answer.class);
		criteria.createAlias("Answer.questio","question");
		List<HashMap<String,Object>> results = basicFinderService.findByCriteria(criteria);
		for(HashMap<String,Object> result : results){
			System.out.println("----------------------");
			for(String key : result.keySet()){
				System.out.println("Key "+key+" Value "+result.get(key));
			}
			System.out.println("----------------------");
		}
	}

	public void createQuestion() {
		HashMap<Long,Integer> numOfQuestion = calNumOfQuestion(this.getExamId(),this.getNumOfQuestion());
		checkNumOfQuestion(numOfQuestion);
	}
	
	
	/**
	 *  if requestQuestion > numOfQuestion throws QuestionNotEnoughException()
	 */
	public void checkNumOfQuestion(HashMap<Long,Integer> numOfQuestion){
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
			Integer requestNumOfQuestion = BeanUtils.toInteger(numOfQuestion.get(questionGroupId));
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
	public HashMap<Long,Integer> calNumOfQuestion(Long examId,Integer nOfQ) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamQuestionGroup.class,"examQuestionGroup");
		
		criteria.add(Restrictions.eq("examQuestionGroup.examId", examId));
		
		List<ExamQuestionGroup> results = basicFinderService.findByCriteria(criteria);
		
		HashMap<Long,Integer> numOfQuestion = new HashMap<Long,Integer>();
		
		Integer total = 0;
		
		HashMap<Long,Float> fragment = new HashMap<Long,Float>();
		
		for(ExamQuestionGroup result : results){
			
			BigDecimal nOfQuestion = BeanUtils.toBigDecimal(
					( result.getQuestionPercent().floatValue() / 100)* nOfQ )
					.setScale(2, RoundingMode.HALF_UP);
			numOfQuestion.put(result.getQuestionGroupId()
					,nOfQuestion.setScale(0, RoundingMode.FLOOR).intValue() );
			
			fragment.put(result.getQuestionGroupId()
					,BeanUtils.toFloat(nOfQuestion.floatValue() % 100));
			
			total+= nOfQuestion.setScale(0, RoundingMode.FLOOR).intValue();
		}
		
		updateNumOfQuestion(numOfQuestion,fragment,nOfQ - total);
		

		return numOfQuestion;
	}
	
	public void updateNumOfQuestion(HashMap<Long,Integer> numOfQuestion,HashMap<Long,Float> fragment,Integer size) {
		
		Long maxKey = null;
		for(int i=1;i<=size;i++){
			for(Long key : fragment.keySet()){
				if(BeanUtils.isNull(maxKey)){
					maxKey = key;
				}
				if (numOfQuestion.get(key) == 0){
					maxKey = key;
					break;
				}else if(fragment.get(key) > fragment.get(maxKey)){
					maxKey = key;
				}
			}
			Integer nOfQ = numOfQuestion.get(maxKey);
			nOfQ++;
			numOfQuestion.put(maxKey, nOfQ);
			fragment.remove(maxKey);
			maxKey = null;
		}
	}
	
}
