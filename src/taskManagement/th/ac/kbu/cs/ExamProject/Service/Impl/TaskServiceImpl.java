package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;

	@Override
	@Transactional(noRollbackFor=Exception.class)
	public void assignTask(AssignmentTask assignmentTask, List<Long> sectionIdList) {
		Long assignmentTaskId = (Long)this.basicEntityService.save(assignmentTask);
		List<AssignmentSection> assignmentSectionList = new ArrayList<AssignmentSection>();
		
		for(Long sectionId : sectionIdList){
			AssignmentSection assignmentSection = new AssignmentSection();
			assignmentSection.setAssignmentTaskId(assignmentTaskId);
			assignmentSection.setSectionId(sectionId);
			
			assignmentSectionList.add(assignmentSection);
		}
		
		
		this.basicEntityService.save(assignmentSectionList);
	}
	
	
	
}
