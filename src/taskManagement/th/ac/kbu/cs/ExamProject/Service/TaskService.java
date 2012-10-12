package th.ac.kbu.cs.ExamProject.Service;

import java.util.List;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;

public interface TaskService {
	public void assignTask(AssignmentTask assignmentTask,List<Long> sectionIdList);
	public void editTask(AssignmentTask assignmentTask
			,List<AssignmentSection> saveSection
			,List<AssignmentSection> updateSection
			,List<AssignmentSection> delSection);
	public void updateWork(AssignmentWork assignmentWork);
	
}
