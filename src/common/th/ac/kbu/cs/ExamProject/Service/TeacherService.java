package th.ac.kbu.cs.ExamProject.Service;

import th.ac.kbu.cs.ExamProject.Entity.Teacher;
public interface TeacherService {
	public void save(Teacher teacher);
	public void saveOrUpdate(Teacher teacher);
	public void delete(Teacher teacher);
}
