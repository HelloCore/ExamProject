package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.Register;
import th.ac.kbu.cs.ExamProject.Entity.TeacherCourse;
import th.ac.kbu.cs.ExamProject.Exception.DontHavePermissionException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class RegisterCoreGridManagement extends CoreGridManager<RegisterManagementDomain>{

	@Override
	public String getDeleteString(RegisterManagementDomain domain) {
		return null;
	}

	@Override
	public Object toEntity(RegisterManagementDomain domain) {
		return null;
	}

	private void validateCourse(RegisterManagementDomain domain){
		if(BeanUtils.isEmpty(domain.getCourseId())){
			throw new ParameterNotFoundException("parameter not found");
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		criteria.setProjection(Projections.rowCount());
		
		criteria.add(Restrictions.eq("teacherCourse.username", SecurityUtils.getUsername()));
		criteria.add(Restrictions.eq("teacherCourse.courseId", domain.getCourseId()));
		
		Long row = this.basicFinderService.findUniqueByCriteria(criteria);
		if(row <= 0L){
			throw new DontHavePermissionException("dont have permission");
		}
	}
	@Override
	protected void setProjectionList(ProjectionList projectionList,
			RegisterManagementDomain domain) {
		projectionList.add(Projections.property("register.requestDate"),"requestDate");
		projectionList.add(Projections.property("register.processDate"),"processDate");
		projectionList.add(Projections.property("register.status"),"status");
		projectionList.add(Projections.property("register.verifyBy"),"verifyBy");
		projectionList.add(Projections.property("section.sectionName"),"sectionName");
		projectionList.add(Projections.property("section.sectionYear"),"sectionYear");
		projectionList.add(Projections.property("section.sectionSemester"),"sectionSemester");
		projectionList.add(Projections.property("section.sectionName"),"sectionName");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		projectionList.add(Projections.property("user.username"),"studentId");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");	
	}

	@Override
	protected DetachedCriteria initCriteria(RegisterManagementDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Register.class,"register");
		criteria.createAlias("register.section", "section");
		criteria.createAlias("section.course", "course");
		criteria.createAlias("register.user", "user");
		return criteria;
	}

	@Override
	protected DetachedCriteria initCriteriaTeacher(RegisterManagementDomain domain) {
		this.validateCourse(domain);
		DetachedCriteria criteria = DetachedCriteria.forClass(Register.class,"register");
		criteria.createAlias("register.section", "section");
		criteria.createAlias("section.course", "course");
		criteria.createAlias("register.user", "user");
		return criteria;
	}

	@Override
	protected void addOrder(DetachedCriteria criteria) {
		if(BeanUtils.isNotEmpty(getOrder()) && BeanUtils.isNotEmpty(getOrderBy())){
			if(getOrder().equalsIgnoreCase("asc")){
				criteria.addOrder(Order.asc(getOrderBy()));
			}else{
				criteria.addOrder(Order.desc(getOrderBy()));
			}
		}
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria,
			RegisterManagementDomain domain) {
		// TODO Auto-generated method stub
		
	}

}
