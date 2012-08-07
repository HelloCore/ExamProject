package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.Answer;
import th.ac.kbu.cs.ExamProject.Entity.Question;
import th.ac.kbu.cs.ExamProject.Service.QuestionAnswerService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class QuestionCoreGridManager extends CoreGridManager<QuestionDomain>{

	@Autowired
	private QuestionAnswerService basicEntityService;

	@Override 
	public CoreGrid<HashMap<String,Object>> searchAdmin(QuestionDomain domain){
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" SELECT ").append("question.questionId").append(" as ").append("questionId")
								.append(",").append("question.questionText").append(" as ").append("questionText")
								.append(",").append("questionGroup.questionGroupName").append(" as ").append("questionGroupName")
								.append(",").append("course.courseCode").append(" as ").append("courseCode")
								.append(",").append("( SELECT COUNT(*) FROM Answer answer Where answer.questionId=question.questionId ")
											.append(" AND answer.flag=1 AND answer.answerScore=0 ").append(") as numOfFoolAnswer")
								.append(",").append("( SELECT COUNT(*) FROM Answer answer Where answer.questionId=question.questionId ")
											.append(" AND answer.flag=1 AND answer.answerScore=1 ").append(") as numOfCorrectAnswer ")
				.append(" FROM ").append(" Question question ")
				.append(" JOIN question.questionGroup questionGroup ")
				.append(" JOIN questionGroup.course course ")
				.append(" WHERE ").append(" question.flag=1 ").append("AND course.courseId=").append(domain.getCourseId());

		if(BeanUtils.isNotEmpty(domain.getQuestionGroupId()) && (domain.getQuestionGroupId() != 0)){
			sqlString.append(" AND questionGroup.questionGroupId=").append(domain.getQuestionGroupId());
		}
		if(BeanUtils.isNotEmpty(domain.getQuestionText())){
			sqlString.append(" AND question.questionText like %").append(domain.getQuestionText()).append("%");
		}
		List<HashMap<String,Object>> records =  basicFinderService.find(sqlString.toString());
		CoreGrid<HashMap<String,Object>> gridData = new CoreGrid<HashMap<String,Object>>();
		gridData.setRecords(records);
		Integer totalRecord = this.getTotal(domain);
		gridData.setTotalRecords(totalRecord);
		gridData.setPage(this.getPage());
		Double totalPages = Math.ceil(totalRecord.doubleValue() / this.getRows().doubleValue());
		gridData.setTotalPages(totalPages.intValue());
		
		
		return gridData;
	}
	
	@Override
	public void delete(QuestionDomain domain) {
		Question entity = this.toEntity(domain);
		entity.setFlag(false);
		basicEntityService.updateQuestion(entity);
	}
	
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
	protected DetachedCriteria initCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Answer.class,"answer");
		criteria.createAlias("answer.question", "question");
		criteria.createAlias("question.questionGroup", "questionGroup");
		criteria.createAlias("questionGroup.course", "course");
		return criteria;
	}

	@Override
	protected DetachedCriteria initCriteriaTeacher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addOrder(DetachedCriteria criteria) {
		criteria.addOrder(Order.asc("question.questionId"));
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria,
			QuestionDomain domain) {
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
	protected Integer getTotal(QuestionDomain domain){
		DetachedCriteria criteria = initCriteria();
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.countDistinct("question.questionId"));
		criteria.setProjection(projectionList);
		applyCriteria(criteria,domain);
		return BeanUtils.toInteger(basicFinderService.findUniqueByCriteria(criteria));
	}

	@Override
	public String getDeletString(QuestionDomain domain) {
		return null;
	}

}
