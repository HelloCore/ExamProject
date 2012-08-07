package th.ac.kbu.cs.ExamProject.Domain;

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
import th.ac.kbu.cs.ExamProject.Entity.Question;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.QuestionAnswerService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Configurable
public class ViewQuestionDomain extends ViewQuestionPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;

	@Autowired
	private QuestionAnswerService questionAnswerService;
	
	public HashMap<String,Object> getQuestionData(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Question.class,"question");
		criteria.createAlias("question.questionGroup", "questionGroup");
		criteria.createAlias("questionGroup.course", "course");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("question.questionId"), "questionId");
		projectionList.add(Projections.property("question.questionText"), "questionText");
		projectionList.add(Projections.property("questionGroup.questionGroupName"), "questionGroupName");
		projectionList.add(Projections.property("course.courseCode"), "courseCode");
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("question.questionId", this.getQuestionId()));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public List<HashMap<String,Object>> getAnswerData(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Answer.class,"answer");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("answer.answerId"), "answerId");
		projectionList.add(Projections.property("answer.answerText"), "answerText");
		projectionList.add(Projections.property("answer.answerScore"), "answerScore");
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("answer.questionId", this.getQuestionId()));
		criteria.add(Restrictions.eq("answer.flag",	true));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return basicFinderService.findByCriteria(criteria);
	}

	public void editQuestionParent() {
		if(BeanUtils.isNotEmpty(this.getQuestionId())){
			DetachedCriteria criteria = DetachedCriteria.forClass(Question.class,"question");
			criteria.add(Restrictions.eq("question.questionId", this.getQuestionId()));
			Question question = basicFinderService.findUniqueByCriteria(criteria);
			if(BeanUtils.isNotEmpty(this.getQuestionGroupId())){
				question.setQuestionGroupId(getQuestionGroupId());
			}
			questionAnswerService.updateQuestion(question);
		}
	}

	public void editQuestionText() {
		if(BeanUtils.isNotEmpty(this.getQuestionId())){
			DetachedCriteria criteria = DetachedCriteria.forClass(Question.class,"question");
			criteria.add(Restrictions.eq("question.questionId", this.getQuestionId()));
			Question question = basicFinderService.findUniqueByCriteria(criteria);
			if(BeanUtils.isNotEmpty(this.getQuestionText())){
				question.setQuestionText(getQuestionText());
			}
			questionAnswerService.updateQuestion(question);
		}
	}

	public void deleteAnswer() {
		if(BeanUtils.isNotEmpty(this.getAnswerId())){
			DetachedCriteria criteria = DetachedCriteria.forClass(Answer.class,"answer");
			criteria.add(Restrictions.eq("answer.answerId", this.getAnswerId()));
			Answer answer = basicFinderService.findUniqueByCriteria(criteria);
			answer.setFlag(false);
			questionAnswerService.updateAnswer(answer);
		}
	}

	public void editAnswer() {
		if(BeanUtils.isNotEmpty(this.getAnswerId())){
			DetachedCriteria criteria = DetachedCriteria.forClass(Answer.class,"answer");
			criteria.add(Restrictions.eq("answer.answerId", this.getAnswerId()));
			Answer answer = basicFinderService.findUniqueByCriteria(criteria);
			answer.setAnswerText(this.getAnswerText());
			answer.setAnswerScore(this.getAnswerScore());
	
			questionAnswerService.updateAnswer(answer);
		}
	}

	public Answer addAnswer() throws Exception {
		Answer answer = new Answer();
		if(BeanUtils.isNotEmpty(getQuestionId())){
			answer.setQuestionId(getQuestionId());
			answer.setAnswerText(getAnswerText());
			answer.setAnswerScore(getAnswerScore());
			answer.setFlag(true);
			answer.setAnswerId((Long) questionAnswerService.saveAnswer(answer));
		}else{
			throw new Exception();
		}
		return answer;
	}
	
	
}
