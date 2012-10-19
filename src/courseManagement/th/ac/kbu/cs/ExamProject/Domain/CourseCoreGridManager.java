package th.ac.kbu.cs.ExamProject.Domain;

import java.io.Serializable;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.Course;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class CourseCoreGridManager extends CoreGridManager<CourseDomain> {
	
	public Serializable saveAndReturn(CourseDomain domain) {
		Object entity = this.toEntity(domain);
		return this.basicEntityService.save(entity);
	}
	
	@Override
	public Object toEntityDelete(CourseDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Course.class,"course");
		criteria.add(Restrictions.eq("course.courseId",domain.getCourseId()));
		
		Course course = this.basicFinderService.findUniqueByCriteria(criteria);
		course.setFlag(false);
		
		return course;
	}
	
	@Override
	public Object toEntity(CourseDomain domain) {
		Course course = new Course();
		if(BeanUtils.isNotEmpty(domain.getCourseId())){
			course.setCourseId(domain.getCourseId());
		}
		if(BeanUtils.isNotEmpty(domain.getCourseCode())){
			course.setCourseCode(domain.getCourseCode());
		}
		if(BeanUtils.isNotEmpty(domain.getCourseName())){
			course.setCourseName(domain.getCourseName());
		}
		course.setFlag(true);
		return course;
	}
	
	@Override
	protected void setProjectionList(ProjectionList projectionList,
			CourseDomain domain) {
		projectionList.add(Projections.property("course.courseId"),"courseId");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		projectionList.add(Projections.property("course.courseName"),"courseName");
	}
	
	@Override
	protected DetachedCriteria initCriteria(CourseDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Course.class,"course");
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
	protected void applyCriteria(DetachedCriteria criteria, CourseDomain domain,
			String username) {
		criteria.add(Restrictions.eq("course.flag", true));
		if(BeanUtils.isNotEmpty(domain.getCourseCode())){
			criteria.add(Restrictions.ilike("course.courseCode", domain.getCourseCode(), MatchMode.ANYWHERE));
		}
		if(BeanUtils.isNotEmpty(domain.getCourseName())){
			criteria.add(Restrictions.ilike("course.courseName", domain.getCourseName(), MatchMode.ANYWHERE));
		}
		
	}
}