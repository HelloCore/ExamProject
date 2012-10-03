package th.ac.kbu.cs.ExamProject.Service;

import java.util.List;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;

public interface TaskService {
	public void assignTask(AssignmentTask assignmentTask,List<Long> sectionIdList);
}
