package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.multipart.MultipartFile;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;
import th.ac.kbu.cs.ExamProject.Exception.AssignmentException;
import th.ac.kbu.cs.ExamProject.Service.AssignmentService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class SubmitAssignmentDomain extends SubmitAssignmentPrototype{
	@Autowired
	private AssignmentService assignmentService;
	
	private void validateParameter(){
		if(BeanUtils.isNull(this.getUploadFile())
				|| BeanUtils.isEmpty(this.getAssignmentId())){
			throw new AssignmentException("Parameter not found!");
		}
	}
	
	private void validateAssignmentTask(AssignmentTask assignmentTask){
		Date today = new Date();
		if(assignmentTask.getStartDate().after(today)){
			throw new AssignmentException("Assignment not start");
		}
		if(assignmentTask.getEndDate().before(today)){
			throw new AssignmentException("Assignment is expired");
		}
		Long fileSizeBytes = 0L;
		Integer numOfFile =0;
		for(MultipartFile multipartFile : this.getUploadFile()){
			if(multipartFile.getSize() > 0L){
				fileSizeBytes += multipartFile.getSize();
				numOfFile++;
			}
		}
		
		if(assignmentTask.getNumOfFile() < numOfFile){
			throw new AssignmentException("Num of file exception");
		}
		
		if(assignmentTask.getLimitFileSizeKb() < (fileSizeBytes/1024)){
			throw new AssignmentException("Limit file size exception");
		}
		
	}
	
	public void submitAssignment(){
		validateParameter();
		
		validateSubmitAssignment(this.getAssignmentId(),SecurityUtils.getUsername());
		
		AssignmentTask assignmentTask = this.assignmentService.getAssignmentEntity(getAssignmentId(), SecurityUtils.getUsername());

		validateAssignmentTask(assignmentTask);
		
		AssignmentWork assignmentWork = new AssignmentWork();
		assignmentWork.setAssignmentTaskId(this.getAssignmentId());
		assignmentWork.setSendBy(SecurityUtils.getUsername());
		assignmentWork.setSendDate(new Date());
		assignmentWork.setStatus(0);
		try{
			this.assignmentService.submitAssignment(assignmentWork, getUploadFile());
		}catch(Exception ex){
			throw new AssignmentException(ex.getMessage());
		}
	}

	private void validateSubmitAssignment(Long assignmentId, String username) {
		Boolean isValid = this.assignmentService.validateSubmitAssignment(assignmentId, username);
		if(!isValid){
			throw new AssignmentException("cant submit assignment anymore");
		}
	}

}
