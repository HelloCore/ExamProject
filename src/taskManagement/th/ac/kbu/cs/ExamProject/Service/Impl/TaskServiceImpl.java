package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
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

	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void editTask(AssignmentTask assignmentTask,
			List<AssignmentSection> saveSection,
			List<AssignmentSection> updateSection,
			List<AssignmentSection> delSection) {
		this.basicEntityService.deleteAll(delSection);
		this.basicEntityService.update(updateSection);
		this.basicEntityService.save(saveSection);
		
		this.basicEntityService.update(assignmentTask);
	}

	@Override
	public void updateWork(AssignmentWork assignmentWork) {
		this.basicEntityService.update(assignmentWork);
	}

	
}
