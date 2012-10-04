package th.ac.kbu.cs.ExamProject.Service;

import java.util.HashMap;
import java.util.List;

public interface StudentTeacherService {
	void validateCourse(String username,Long courseId);
	Boolean validateCourseId(String username,Long courseId);
	Boolean validateStudentCourseId(String username,Long courseId);
	List<Long> getCourseId(String username);
	List<Long> getStudentCourseId(String username);
	List<HashMap<String,Object>> getCourseData(String username);
	List<Long> getSectionId(String username);
}
