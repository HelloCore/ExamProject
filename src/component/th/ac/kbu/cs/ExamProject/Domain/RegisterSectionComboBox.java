package th.ac.kbu.cs.ExamProject.Domain;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.Entity.TeacherCourse;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class RegisterSectionComboBox extends ComboBox{
	
	private Long courseId;
	private Long sectionId;
	
	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	private void validateParameter(){
		if(BeanUtils.isEmpty(this.getCourseId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public List<Object[]> getSectionAdminData(){
		validateParameter();
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
							.append(" section.sectionId ")
							.append(" ,section.sectionName ")
							.append(" ,section.sectionYear ")
							.append(" ,section.sectionSemester ")
					.append(" FROM Section section ")
					.append(" WHERE section.flag<> 0 ")
					.append(" AND section.status<> 0 ")
					.append(" AND section.courseId = ? ");
		return basicFinderService.find(queryString.toString(), this.getCourseId());
	}
	
	private void validateTeacherPermission(String username){
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
	
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount());
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("teacherCourse.username", username));
		Long results = basicFinderService.findUniqueByCriteria(criteria);
		if(results==0){
			throw new CoreException(CoreExceptionMessage.PERMISSION_DENIED);
		}
	}

	public List<Object[]> getSectionTeacherData(String username){
		validateParameter();
		validateTeacherPermission(username);
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
							.append(" section.sectionId ")
							.append(" ,section.sectionName ")
							.append(" ,section.sectionYear ")
							.append(" ,section.sectionSemester ")
					.append(" FROM Section section ")
					.append(" WHERE section.courseId = ? ")
					.append(" AND section.flag<>0 ")
					.append(" AND section.status<>0 ");
		return basicFinderService.find(queryString.toString(), this.getCourseId());
	}
	

	public List<Object[]> getCourseSectionDataTeacher(String username){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
						.append(" section.sectionId ")
						.append(" ,section.sectionName ")
						.append(" ,section.sectionYear ")
						.append(" ,section.sectionSemester ")
						.append(" ,course.courseId ")
						.append(" ,course.courseCode ")
				.append(" FROM Section section ")
				.append(" JOIN section.course course ")
				.append(" WHERE section.flag<>0 ")
				.append(" AND course.courseId in  ?  ")
				.append(" ORDER BY course.courseId,section.sectionYear,section.sectionSemester,section.sectionName ");
		return basicFinderService.find(queryString.toString(), this.teacherService.getCourseId(username).toArray());					
	}
	
	public List<Object[]> getCourseSectionData(String username){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
						.append(" section.sectionId ")
						.append(" ,section.sectionName ")
						.append(" ,section.sectionYear ")
						.append(" ,section.sectionSemester ")
						.append(" ,course.courseId ")
						.append(" ,course.courseCode ")
				.append(" FROM Section section ")
				.append(" JOIN section.course course ")
				.append(" WHERE section.flag<>0 ")
				.append(" AND section.status<>0 ")
				.append(" AND CONCAT(section.courseId,section.sectionSemester,section.sectionYear) NOT IN ")
				.append(" ( SELECT CONCAT(section.courseId,section.sectionSemester,section.sectionYear) ")
					.append(" FROM StudentSection studentSection ") 
					.append(" JOIN studentSection.section section ")
					.append(" WHERE studentSection.username = ? ")
				.append(" ) AND section.sectionId NOT IN  ")
					.append(" ( SELECT register.sectionId ")
						.append(" FROM Register register ")
						.append(" WHERE (register.status = 0 OR ")
							.append(" register.status = 1 OR ")
							.append(" register.status = 3 ) AND ")
							.append(" register.username = ? )")
					.append(") ORDER BY course.courseId ");
		return basicFinderService.find(queryString.toString(),new Object[]{ username,username });					
	}
	
	public List<Object[]> getSectionStudentData(String username) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
							.append(" section.sectionId ")
							.append(" ,section.sectionName ")
							.append(" ,section.sectionYear ")
							.append(" ,section.sectionSemester ")
					.append(" FROM Section section ")
					.append(" WHERE section.flag<>0 ")
					.append(" AND section.status<>0 ")
					.append(" AND section.courseId = ? ")
					.append(" AND CONCAT(section.sectionSemester,section.sectionYear) NOT IN ")
					.append(" ( SELECT CONCAT(section.sectionSemester,section.sectionYear) ")
						.append(" FROM StudentSection studentSection ") 
						.append(" JOIN studentSection.section section ")
						.append(" WHERE studentSection.username = ? ")
						.append(" AND section.courseId = ? ")
					.append(" ) ");
		
		return basicFinderService.find(queryString.toString(),new Object[]{ this.getCourseId(), username, this.getCourseId() });
	}

	public List<Object[]> getChangeSectionStudentData(String username) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
							.append(" section.sectionId ")
							.append(" ,section.sectionName ")
							.append(" ,section.sectionYear ")
							.append(" ,section.sectionSemester ")
					.append(" FROM Section section ")
					.append(" WHERE section.flag<>0 ")
					.append(" AND section.status<>0 ")
					.append(" AND section.courseId = ? ")
					.append(" AND CONCAT(section.sectionSemester,section.sectionYear) IN ")
					.append(" ( SELECT CONCAT(section.sectionSemester,section.sectionYear) ")
						.append(" FROM StudentSection studentSection ") 
						.append(" JOIN studentSection.section section ")
						.append(" WHERE studentSection.username = ? ")
						.append(" AND section.courseId = ? ")
					.append(" ) AND section.sectionId <> ?");
		return basicFinderService.find(queryString.toString(),new Object[]{ this.getCourseId(), username, this.getCourseId(),this.getSectionId() });
	}
	
}
