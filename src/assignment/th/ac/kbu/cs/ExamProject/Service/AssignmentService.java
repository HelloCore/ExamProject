package th.ac.kbu.cs.ExamProject.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;

public interface AssignmentService {
	public void submitAssignment(AssignmentWork assignmentWork,List<MultipartFile> uploadFile) throws IOException;
}
