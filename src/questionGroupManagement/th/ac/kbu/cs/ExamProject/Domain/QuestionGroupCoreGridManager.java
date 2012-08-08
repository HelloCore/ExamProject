package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.QuestionGroup;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class QuestionGroupCoreGridManager extends CoreGridManager<QuestionGroupDomain>{

	@Override
	public Object toEntity(QuestionGroupDomain domain) {
		QuestionGroup questionGroup = new QuestionGroup();
		if(BeanUtils.isNotNull(domain.getQuestionGroupId())){
			questionGroup.setQuestionGroupId(domain.getQuestionGroupId());
		}
		if(BeanUtils.isNotNull(domain.getQuestionGroupName())){
			questionGroup.setQuestionGroupName(domain.getQuestionGroupName());
		}
		if(BeanUtils.isNotNull(domain.getCourseId())){
			questionGroup.setCourseId(domain.getCourseId());
		}
		questionGroup.setFlag(true);
		return questionGroup;
	}

	@Override
	protected void setProjectionList(ProjectionList projectionList,
			QuestionGroupDomain domain) {
		projectionList.add(Projections.property("questionGroup.questionGroupId"),"questionGroupId");
		projectionList.add(Projections.property("questionGroup.questionGroupName"),"questionGroupName");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
	}

	@Override
	protected DetachedCriteria initCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(QuestionGroup.class,"questionGroup");
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
		if(BeanUtils.isNotNull(getOrder()) && BeanUtils.isNotNull(getOrderBy())){
			if(getOrder().equalsIgnoreCase("asc")){
				criteria.addOrder(Order.asc(getOrderBy()));
			}else{
				criteria.addOrder(Order.desc(getOrderBy()));
			}
		}
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria,
			QuestionGroupDomain domain) {
		if(BeanUtils.isNotEmpty(domain.getQuestionGroupNameSearch())){
			criteria.add(Restrictions.ilike("questionGroup.questionGroupName", domain.getQuestionGroupNameSearch(), MatchMode.ANYWHERE));
		}
		if(BeanUtils.isNotEmpty(domain.getCourseCodeSearch())){
			criteria.add(Restrictions.ilike("course.courseCode", domain.getCourseCodeSearch(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("questionGroup.flag", true));
	}

	@Override
	public String getDeleteString(QuestionGroupDomain domain) {
		return "UPDATE QuestionGroup questionGroup SET flag=false WHERE questionGroup.questionGroupId="+domain.getQuestionGroupId();
	}

}
