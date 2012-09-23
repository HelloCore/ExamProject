package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.Answer;
import th.ac.kbu.cs.ExamProject.Entity.Question;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class QuestionCoreGridManager extends CoreGridManager<QuestionDomain>{
	
	@Override
	public Question toEntity(QuestionDomain domain) {
		Question question = new Question();
		if(BeanUtils.isNotEmpty(domain.getQuestionId())){
			DetachedCriteria criteria = DetachedCriteria.forClass(Question.class,"question");
			criteria.add(Restrictions.eq("question.questionId", domain.getQuestionId()));
			question = basicFinderService.findUniqueByCriteria(criteria);
		}
		return question;
	}

	@Override
	protected void setProjectionList(ProjectionList projectionList,
			QuestionDomain domain) {
		// TODO Auto-generated method stub
		projectionList.add(Projections.groupProperty("question.questionId"),"questionId");
		projectionList.add(Projections.groupProperty("question.questionText"),"questionText");
		projectionList.add(Projections.groupProperty("questionGroup.questionGroupName"),"questionGroupName");
		projectionList.add(Projections.groupProperty("course.courseCode"),"courseCode");
		projectionList.add(Projections.count("answer.answerId"),"numOfAnswer");
	}

	@Override
	protected DetachedCriteria initCriteria(QuestionDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Answer.class,"answer");
		criteria.createAlias("answer.question", "question");
		criteria.createAlias("question.questionGroup", "questionGroup");
		criteria.createAlias("questionGroup.course", "course");
		return criteria;
	}


	@Override
	protected void addOrder(DetachedCriteria criteria) {
		criteria.addOrder(Order.asc("question.questionId"));
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria,
			QuestionDomain domain,
			String username) {
		criteria.add(Restrictions.in("course.courseId", this.teacherService.getCourseId(username)));
		
		criteria.add(Restrictions.eq("question.flag", true));
		criteria.add(Restrictions.eq("answer.flag", true));
		if(BeanUtils.isNotEmpty(domain.getCourseId())){
			criteria.add(Restrictions.eq("course.courseId",	domain.getCourseId()));
		}
		if(BeanUtils.isNotEmpty(domain.getQuestionGroupId()) && (domain.getQuestionGroupId() != 0)){
			criteria.add(Restrictions.eq("questionGroup.questionGroupId", domain.getQuestionGroupId()));
		}
		if(BeanUtils.isNotEmpty(domain.getQuestionText())){
			criteria.add(Restrictions.like("question.questionText", domain.getQuestionText(), MatchMode.ANYWHERE));
		}
	}

	@Override
	protected void setProjectionCount(ProjectionList projectionList,QuestionDomain domain){
		projectionList.add(Projections.countDistinct("question.questionId"));
		
	}

	@Override
	public Object toEntityDelete(QuestionDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Question.class,"question");
		criteria.add(Restrictions.eq("question.questionId",domain.getQuestionId()));
		
		Question question = this.basicFinderService.findUniqueByCriteria(criteria);
		question.setFlag(false);
		return question;
	}
	
	
}
