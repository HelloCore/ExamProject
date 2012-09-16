package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class ExamReportCoreGridManager extends CoreGridManager<ResultExamDomain>{
	
	@Override
	protected void setProjectionList(ProjectionList projectionList,
			ResultExamDomain domain) {
		projectionList.add(Projections.property("examResult.examCompleteDate"),"examCompleteDate");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		projectionList.add(Projections.property("exam.examHeader"),"examHeader");
		projectionList.add(Projections.property("examResult.examCount"),"examCount");
		projectionList.add(Projections.property("examResult.numOfQuestion"),"numOfQuestion");
		projectionList.add(Projections.property("examResult.examUsedTime"),"examUsedTime");
		projectionList.add(Projections.property("examResult.examScore"),"examScore");
	}

	@Override
	protected DetachedCriteria initCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResult.class,"examResult");
		criteria.createAlias("examResult.exam", "exam");
		criteria.createAlias("exam.course", "course");
		return criteria;
	}

	@Override
	protected DetachedCriteria initCriteriaTeacher() {
//		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
//		criteria.createAlias("teacherCourse.course", "course");
//		criteria.createAlias("teacherCourse.user", "user");
//		criteria.createAlias("course.section", "section");
//		return criteria;
		return null;
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria, ResultExamDomain domain) {
		criteria.add(Restrictions.eq("examResult.username", SecurityUtils.getUsername()));
		criteria.add(Restrictions.eq("examResult.examCompleted", true));
//		criteria.addOrder(Order.desc("examResult.examCompleteDate"));
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
	public Object toEntity(ResultExamDomain domain) {
		return null;
	}
	
	@Override
	public String getDeleteString(ResultExamDomain domain) {
		return null;
//		return "UPDATE Section section SET flag=false WHERE section.sectionId="+domain.getSectionId();
	}


}
