package th.ac.kbu.cs.ExamProject.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;

public interface AssignmentService {
	public List<HashMap<String,Object>> getAssignmentData(String username);
	public HashMap<String, Object> getAssignmentDetail(Long assignmentId,String username);
	public AssignmentTask getAssignmentEntity(Long assignmentId,String username);
	
	public void submitAssignment(AssignmentWork assignmentWork,List<MultipartFile> uploadFile) throws IOException;
	
	public Boolean validateSubmitAssignment(Long assignmentId,String username);
	
}
