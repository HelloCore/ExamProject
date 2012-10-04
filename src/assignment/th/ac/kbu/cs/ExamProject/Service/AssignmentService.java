package th.ac.kbu.cs.ExamProject.Service;

import java.util.HashMap;
import java.util.List;

public interface AssignmentService {
	public List<HashMap<String,Object>> getAssignmentData(String username);
	public HashMap<String, Object> getAssignmentDetail(Long assignmentId,String username);
}
