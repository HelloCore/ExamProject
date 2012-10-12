package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.multipart.MultipartFile;

import th.ac.kbu.cs.ExamProject.Description.PathDescription;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Exception.CoreRedirectException;
import th.ac.kbu.cs.ExamProject.Service.AssignmentService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class SubmitAssignmentDomain extends SubmitAssignmentPrototype{
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private StudentTeacherService studentTeacherService;
	
	private void validateParameter(){
		if(BeanUtils.isNull(this.getUploadFile())
				|| BeanUtils.isEmpty(this.getAssignmentId())){
			throw new CoreRedirectException(CoreExceptionMessage.PARAMETER_NOT_FOUND, PathDescription.Assignment.SELECT);
		}
	}
	
	private void validateAssignmentTask(AssignmentTask assignmentTask){
		Date today = new Date();
		if(assignmentTask.getStartDate().after(today)){
			throw new CoreRedirectException(CoreExceptionMessage.ASSIGNMENT_NOT_STARTED, PathDescription.Assignment.SELECT);
		}
		if(assignmentTask.getEndDate().before(today)){
			throw new CoreRedirectException(CoreExceptionMessage.ASSIGNMENT_EXPIRED, PathDescription.Assignment.SELECT);
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
			throw new CoreRedirectException(CoreExceptionMessage.LIMIT_NUM_OF_FILE, PathDescription.Assignment.SELECT);
		}
		
		if(assignmentTask.getLimitFileSizeKb() < (fileSizeBytes/1024)){
			throw new CoreRedirectException(CoreExceptionMessage.LIMIT_FILE_SIZE, PathDescription.Assignment.SELECT);
		}
		
	}
	
	public AssignmentTask getAssignmentEntity(Long assignmentId, String username) {
		
		if(!studentTeacherService.validateAssignmentSection(assignmentId, username)){
			throw new CoreRedirectException(CoreExceptionMessage.PERMISSION_DENIED, PathDescription.Assignment.SELECT);
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentTask.class,"assignmentTask");
		criteria.add(Restrictions.eq("assignmentTask.assignmentTaskId", assignmentId));

		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public void submitAssignment(){
		validateParameter();
		
		validateSubmitAssignment(this.getAssignmentId(),SecurityUtils.getUsername());
		
		AssignmentTask assignmentTask = this.getAssignmentEntity(getAssignmentId(), SecurityUtils.getUsername());

		validateAssignmentTask(assignmentTask);
		
		AssignmentWork assignmentWork = new AssignmentWork();
		assignmentWork.setAssignmentTaskId(this.getAssignmentId());
		assignmentWork.setSendBy(SecurityUtils.getUsername());
		assignmentWork.setSendDate(new Date());
		assignmentWork.setStatus(0);
		try{
			this.assignmentService.submitAssignment(assignmentWork, getUploadFile());
		}catch(Exception ex){
			throw new CoreRedirectException(new CoreExceptionMessage(ex.getMessage()), PathDescription.Assignment.SELECT);
		}
	}

	private void validateSubmitAssignment(Long assignmentId, String username) {

		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentWork.class,"assignmentWork");
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("assignmentWork.sendBy", username));
		criteria.add(Restrictions.eq("assignmentWork.assignmentTaskId", assignmentId));
		
		Long rowCount = this.basicFinderService.findUniqueByCriteria(criteria);
		
		if(rowCount > 0L){
			throw new CoreRedirectException(CoreExceptionMessage.CANT_SUBMIT_ANYMORE, PathDescription.Assignment.SELECT);
		}
	}
}
