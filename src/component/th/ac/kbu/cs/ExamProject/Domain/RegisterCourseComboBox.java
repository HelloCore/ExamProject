package th.ac.kbu.cs.ExamProject.Domain;

import java.util.List;

public class RegisterCourseComboBox extends ComboBox{
	public List<Object[]> getCourseData(String username){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
						.append(" course.courseId ")
						.append(" ,course.courseCode ")
					.append(" FROM Course course ")
					.append(" WHERE course.flag<>0 ")
					.append(" AND course.courseId NOT IN ")
					.append(" ( SELECT course.courseId ")
						.append(" FROM Register register ")
						.append(" JOIN register.section section ")
						.append(" JOIN section.course course ")
						.append(" WHERE ( register.status=0 ")
						.append(" OR register.status=1 ")
						.append(" OR register.status=3 )")
						.append(" AND section.flag<>0 ")
						.append(" AND course.flag<>0 ")
						.append(" AND register.username = ? ")
					.append(" ) ");
		
		return basicFinderService.find(queryString.toString(), username);
	}
}
