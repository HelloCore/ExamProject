package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Entity.Course;
import th.ac.kbu.cs.ExamProject.Entity.TeacherCourse;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;


@Configurable
public class CourseDomain extends CoursePrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	public CoreGrid<HashMap<String, Object>> search(
			CourseCoreGridManager gridManager) {
		return gridManager.search(this, SecurityUtils.getUsername());
	}

	public void save(CourseCoreGridManager gridManager) {
		gridManager.save(this);
	}

	public void delete(CourseCoreGridManager gridManager) {
		gridManager.delete(this);
	}
	
	private void validateGetCourse(){
		if(BeanUtils.isEmpty(this.getCourseId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public Course getCourse(){
		this.validateGetCourse();
		DetachedCriteria criteria = DetachedCriteria.forClass(Course.class,"course");
		criteria.add(Restrictions.eq("course.courseId", this.getCourseId()));
		
		return this.basicFinderService.findUniqueByCriteria(criteria); 
	}

	public List<HashMap<String, Object>> searchTeacher() {
		this.validateGetCourse();
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		criteria.createAlias("teacherCourse.user", "user");
		criteria.createAlias("user.prefixName", "prefixName");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("user.username"),"username");
		projectionList.add(Projections.property("prefixName.prefixNameTh"),"prefixNameTh");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
		projectionList.add(Projections.property("user.email"),"email");
		
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		criteria.add(Restrictions.eq("teacherCourse.courseId", this.getCourseId()));
		criteria.addOrder(Order.asc("prefixName.prefixNameTh"));
		criteria.addOrder(Order.asc("user.firstName"));
		criteria.addOrder(Order.asc("user.lastName"));
		
		return this.basicFinderService.findByCriteria(criteria);
	}

	private void validateGetUsername(){
		if(BeanUtils.isEmpty(this.getUsername())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public void saveTeacher() {
		this.validateGetUsername();
		this.validateGetCourse();
		
		TeacherCourse teacherCourse = new TeacherCourse();
		if(BeanUtils.isNotEmpty(this.getCourseId())){
			teacherCourse.setCourseId(this.getCourseId());
		}
		if(BeanUtils.isNotEmpty(this.getUsername())){
			teacherCourse.setUsername(this.getUsername());
		}
		
		this.basicEntityService.save(teacherCourse);
	}

	public void deleteTeacher() {
		this.validateGetUsername();
		this.validateGetCourse();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		criteria.setProjection(Projections.property("teacherCourse.teacherCourseId"));
		
		criteria.add(Restrictions.eq("teacherCourse.courseId", this.getCourseId()));
		criteria.add(Restrictions.eq("teacherCourse.username", this.getUsername()));
	
		Long teacherCourseId = this.basicFinderService.findUniqueByCriteria(criteria);
		TeacherCourse teacherCourse = new TeacherCourse();
		teacherCourse.setTeacherCourseId(teacherCourseId);
		
		
		this.basicEntityService.delete(teacherCourse);
	}
	
	
	
}
