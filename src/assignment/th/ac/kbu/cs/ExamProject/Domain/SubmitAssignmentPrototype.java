package th.ac.kbu.cs.ExamProject.Domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SubmitAssignmentPrototype {
	private Long assignmentId;
	private List<MultipartFile> uploadFile;
	
	public Long getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}
	public List<MultipartFile> getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(List<MultipartFile> uploadFile) {
		this.uploadFile = uploadFile;
	}


}
