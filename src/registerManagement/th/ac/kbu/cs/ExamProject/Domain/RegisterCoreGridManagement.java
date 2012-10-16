package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.Register;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class RegisterCoreGridManagement extends CoreGridManager<RegisterManagementDomain>{


	@Override
	public Object toEntity(RegisterManagementDomain domain) {
		return null;
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
		projectionList.add(Projections.property("prefixName.prefixNameTh"),"prefixNameTh");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");	
	}

	@Override
	protected DetachedCriteria initCriteria(RegisterManagementDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Register.class,"register");
		criteria.createAlias("register.section", "section");
		criteria.createAlias("section.course", "course");
		criteria.createAlias("register.user", "user");
		criteria.createAlias("user.prefixName", "prefixName");
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
			RegisterManagementDomain domain,String username) {
		criteria.add(Restrictions.or(Restrictions.eq("register.status", 1), Restrictions.eq("register.status", 2)));
		criteria.add(Restrictions.in("section.courseId", this.studentTeacherService.getCourseId(username)));
	}

	@Override
	public Object toEntityDelete(RegisterManagementDomain domain) {
		return null;
	}

}
