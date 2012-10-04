package th.ac.kbu.cs.ExamProject.Service;

import java.util.HashMap;
import java.util.List;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;

public interface TaskService {
	public void assignTask(AssignmentTask assignmentTask,List<Long> sectionIdList);
	public AssignmentTask getTaskData(Long taskId);
	public List<HashMap<String,Object>> getSectionData(Long taskId);
	public void editTask(AssignmentTask assignmentTask
			,List<AssignmentSection> saveSection
			,List<AssignmentSection> updateSection
			,List<AssignmentSection> delSection);
}
