package th.ac.kbu.cs.ExamProject.Service;

import java.util.List;

public interface TeacherService {
	void validateCourse(String username,Long courseId);
	List<Long> getCourseId(String username);
}
