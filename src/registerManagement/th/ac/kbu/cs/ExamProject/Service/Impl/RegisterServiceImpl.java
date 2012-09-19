package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.Register;
import th.ac.kbu.cs.ExamProject.Entity.StudentSection;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService{
	
	@Autowired
	private BasicEntityService basicEntityService;

	@Transactional(rollbackFor=Exception.class)
	public void acceptSection(List<Register> registers) {

		Date today = new Date();
		List<StudentSection> studentSections = new ArrayList<StudentSection>();
		for(Register register : registers){
			register.setProcessDate(today);
			register.setStatus(1);
			
			StudentSection studentSection = new StudentSection();
			studentSection.setSectionId(register.getSectionId());
			studentSection.setUsername(register.getUsername());
			studentSections.add(studentSection);
		}
		basicEntityService.update(registers);
		basicEntityService.save(studentSections);
	}

	@Transactional(rollbackFor=Exception.class)
	public void rejectSection(List<Register> registers) {
		Date today = new Date();
		for(Register register : registers){
			register.setProcessDate(today);
			register.setStatus(2);
		}
		basicEntityService.update(registers);
	}

}
