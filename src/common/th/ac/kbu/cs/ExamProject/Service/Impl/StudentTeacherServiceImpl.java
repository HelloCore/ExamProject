package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.StudentSection;
import th.ac.kbu.cs.ExamProject.Entity.TeacherCourse;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;

@Service
public class StudentTeacherServiceImpl implements StudentTeacherService{

	@Autowired
	private BasicFinderService basicFinderService;
	
	@Override
	public void validateCourse(String username,Long courseId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("teacherCourse.courseId", courseId));
		criteria.add(Restrictions.eq("teacherCourse.username", username));
		
		Long rowCount = basicFinderService.findUniqueByCriteria(criteria);
		if (rowCount <= 0L){
			throw new CoreException(CoreExceptionMessage.PERMISSION_DENIED);
		}
	}

	@Override
	public List<Long> getCourseId(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		criteria.setProjection(Projections.property("teacherCourse.courseId"));
		criteria.add(Restrictions.eq("teacherCourse.username", username));
		return basicFinderService.findByCriteria(criteria);
	}

	@Override
	public List<HashMap<String, Object>> getCourseData(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		criteria.createAlias("teacherCourse.course", "course");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("course.courseId"),"courseId");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("teacherCourse.username", username));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return basicFinderService.findByCriteria(criteria);
	}

	@Override
	public List<Long> getStudentCourseId(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StudentSection.class,"studentSection");
		criteria.createAlias("studentSection.section", "section");
		
		criteria.setProjection(Projections.distinct(Projections.property("section.courseId")));
		criteria.add(Restrictions.eq("studentSection.username", username));
		return basicFinderService.findByCriteria(criteria);
	}

	@Override
	public Boolean validateCourseId(String username, Long courseId) {
		Boolean isValid = true;
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("teacherCourse.courseId", courseId));
		criteria.add(Restrictions.eq("teacherCourse.username", username));
		
		Long rowCount = basicFinderService.findUniqueByCriteria(criteria);
		if (rowCount <= 0L){
			isValid=false;
		}
		return isValid;
	}

	@Override
	public Boolean validateStudentCourseId(String username, Long courseId) {
		Boolean isValid = true;
		DetachedCriteria criteria = DetachedCriteria.forClass(StudentSection.class,"studentSection");
		criteria.createAlias("studentSection.section", "section");
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("section.courseId", courseId));
		criteria.add(Restrictions.eq("studentSection.username", username));
		
		Long rowCount = basicFinderService.findUniqueByCriteria(criteria);
		if (rowCount <= 0L){
			isValid=false;
		}
		return isValid;
	}

	@Override
	public List<Long> getSectionId(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StudentSection.class,"studentSection");
		criteria.setProjection(Projections.property("studentSection.sectionId"));
		criteria.add(Restrictions.eq("studentSection.username", username));
		return basicFinderService.findByCriteria(criteria);
	}
	
	public Boolean validateAssignmentSection(Long assignmentId,String username){
		DetachedCriteria subCriteria = DetachedCriteria.forClass(StudentSection.class,"studentSection");
		subCriteria.setProjection(Projections.property("studentSection.sectionId"));
		subCriteria.add(Restrictions.eq("studentSection.username", username));
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentSection.class,"assignmentSection");
		criteria.setProjection(Projections.rowCount());
		
		criteria.add(Restrictions.eq("assignmentSection.assignmentTaskId", assignmentId));
		criteria.add(Subqueries.propertyIn("assignmentSection.sectionId", subCriteria));
		
		Long rowCount = this.basicFinderService.findUniqueByCriteria(criteria);
		Boolean isValid = true;
		if(rowCount <= 0L){
			isValid = false;
		}
		return isValid;
	}

}
