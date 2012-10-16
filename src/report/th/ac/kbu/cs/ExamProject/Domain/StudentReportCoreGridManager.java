package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.StudentSection;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class StudentReportCoreGridManager extends CoreGridManager<StudentReportDomain>{

	@Override
	public Object toEntityDelete(StudentReportDomain domain) {
		return null;
	}

	@Override
	public Object toEntity(StudentReportDomain domain) {
		return null;
	}

	@Override
	protected void setProjectionList(ProjectionList projectionList,
			StudentReportDomain domain) {
		projectionList.add(Projections.distinct(Projections.property("user.username")), "studentId");
		projectionList.add(Projections.property("prefixName.prefixNameTh"),"prefixNameTh");
		projectionList.add(Projections.property("user.firstName"), "firstName");
		projectionList.add(Projections.property("user.lastName"), "lastName");
	}

	@Override
	protected DetachedCriteria initCriteria(StudentReportDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StudentSection.class,"studentSection");
		criteria.createAlias("studentSection.user", "user");
		criteria.createAlias("user.prefixName","prefixName");
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
		}else{
			criteria.addOrder(Order.asc("user.username"));
		}
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria,
			StudentReportDomain domain, String username) {
		if(BeanUtils.isNotNull(domain.getSectionId())){
			criteria.add(Restrictions.eq("studentSection.sectionId"	, domain.getSectionId()));
		}
	}

}
