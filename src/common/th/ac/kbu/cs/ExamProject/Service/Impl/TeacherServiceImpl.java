package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.Teacher;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	@Transactional
	public void save(Teacher teacher)
	{
		basicEntityService.save(teacher);
	}
	
	@Transactional
	public void saveOrUpdate(Teacher teacher)
	{
		basicEntityService.saveOrUpdate(teacher);
	}

	@Transactional
	public void delete(Teacher teacher)
	{
		basicEntityService.delete(teacher);
	}
	
	@Transactional
	public void deleteAll(List<Teacher> teachers)
	{
		basicEntityService.deleteAll(teachers);
	}
	

}
