package th.ac.kbu.cs.ExamProject.Domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentFile;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;
import th.ac.kbu.cs.ExamProject.Exception.DataInValidException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Exception.TaskManagementException;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;
import th.ac.kbu.cs.ExamProject.Service.TaskService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class TaskDomain extends TaskPrototype{
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private StudentTeacherService studentTeacherService;
	
	private void validateAssignTaskData(){
		if(BeanUtils.isEmpty(this.getCourseId())
				|| BeanUtils.isEmpty(this.getSectionIdStr())
				|| BeanUtils.isEmpty(this.getTaskName())
				|| BeanUtils.isEmpty(this.getTaskDesc())
				|| BeanUtils.isEmpty(this.getStartDate())
				|| BeanUtils.isEmpty(this.getNumOfFile())
				|| BeanUtils.isEmpty(this.getLimitFileSizeKb())
				|| BeanUtils.isEmpty(this.getMaxScore())){
			throw new ParameterNotFoundException("parameter not found");
		}
	}
	
	private void validateTask(AssignmentTask assignmentTask){
		if(assignmentTask.getNumOfFile()>10 || assignmentTask.getNumOfFile() < 1){
			throw new DataInValidException("data invalid");
		}
		
		if(assignmentTask.getLimitFileSizeKb()> 204800L ||  assignmentTask.getLimitFileSizeKb() < 1){
			throw new DataInValidException("data invalid");
		}
		
		if(assignmentTask.getMaxScore() < 1 || assignmentTask.getMaxScore() > 10000){
			throw new DataInValidException("data invalid");
		}
	}
	
	public void assignTask(){
		validateAssignTaskData();
		SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ObjectMapper mapper = new ObjectMapper();
		
		AssignmentTask assignmentTask = new AssignmentTask();
		assignmentTask.setCourseId(this.getCourseId());
		assignmentTask.setAssignmentTaskName(this.getTaskName());
		assignmentTask.setAssignmentTaskDesc(this.getTaskDesc());
		List<Long> sectionIdList = new ArrayList<Long>();
		try {
			assignmentTask.setStartDate(parserSDF.parse(this.getStartDate()));
			assignmentTask.setEndDate(parserSDF.parse(this.getEndDate()));
			sectionIdList = mapper.readValue(this.getSectionIdStr(), new TypeReference<ArrayList<Long>>(){});
		} catch (Exception e) {
			throw new DataInValidException(e.getMessage());
		}
		assignmentTask.setNumOfFile(this.getNumOfFile());
		assignmentTask.setLimitFileSizeKb(this.getLimitFileSizeKb());
		assignmentTask.setMaxScore(this.getMaxScore());
		assignmentTask.setCreateBy(SecurityUtils.getUsername());
		assignmentTask.setCreateDate(new Date());
		assignmentTask.setFlag(true);
		assignmentTask.setIsEvaluateComplete(false);
		
		validateTask(assignmentTask);
		taskService.assignTask(assignmentTask, sectionIdList);
		
	}

	public CoreGrid<HashMap<String, Object>> search(
			TaskCoreGridManager gridManager) {
		return gridManager.search(this, SecurityUtils.getUsername());
	}

	public void deleteTask(TaskCoreGridManager gridManager) {
		gridManager.delete(this);
	}
	
	private void validateTaskData(){
		if(BeanUtils.isEmpty(this.getTaskId())){
			throw new ParameterNotFoundException("parameter not found!");
		}
	}

	public AssignmentTask getTaskData() {
		this.validateTaskData();
		return taskService.getTaskData(this.getTaskId());
	}

	public List<HashMap<String,Object>> getSectionData() {
		this.validateTaskData();
		return taskService.getSectionData(this.getTaskId());
	}

	private void validateEditTask(){
		if(BeanUtils.isEmpty(this.getCourseId())
				|| BeanUtils.isEmpty(this.getTaskId())
				|| BeanUtils.isEmpty(this.getOldSectionStr())
				|| BeanUtils.isEmpty(this.getSectionIdStr())
				|| BeanUtils.isEmpty(this.getTaskName())
				|| BeanUtils.isEmpty(this.getTaskDesc())
				|| BeanUtils.isEmpty(this.getStartDate())
				|| BeanUtils.isEmpty(this.getNumOfFile())
				|| BeanUtils.isEmpty(this.getLimitFileSizeKb())
				|| BeanUtils.isEmpty(this.getMaxScore())){
			throw new ParameterNotFoundException("parameter not found");
		}
	}
	
	public void editTask() {
		this.validateEditTask();
		SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ObjectMapper mapper = new ObjectMapper();
		
		AssignmentTask assignmentTask = new AssignmentTask();
		assignmentTask.setAssignmentTaskId(this.getTaskId());
		assignmentTask.setCourseId(this.getCourseId());
		assignmentTask.setAssignmentTaskName(this.getTaskName());
		assignmentTask.setAssignmentTaskDesc(this.getTaskDesc());
		
		List<Long> sectionIdList = null;
		List<AssignmentSection> assignmentSectionList = null;
		try {
			assignmentTask.setStartDate(parserSDF.parse(this.getStartDate()));
			assignmentTask.setEndDate(parserSDF.parse(this.getEndDate()));
			sectionIdList = mapper.readValue(this.getSectionIdStr(), new TypeReference<ArrayList<Long>>(){});
			assignmentSectionList = mapper.readValue(this.getOldSectionStr(), new TypeReference<ArrayList<AssignmentSection>>(){});
		} catch (Exception e) {
			throw new DataInValidException(e.getMessage());
		}
		
		List<AssignmentSection> saveSectionList = new ArrayList<AssignmentSection>();
		List<AssignmentSection> updateSectionList = new ArrayList<AssignmentSection>();
		List<AssignmentSection> delSectionList = new ArrayList<AssignmentSection>();
		for(Long sectionId: sectionIdList){
			if(!assignmentSectionList.isEmpty()){
				AssignmentSection assignmentSection = assignmentSectionList.get(0);
				assignmentSection.setAssignmentTaskId(this.getTaskId());
				assignmentSection.setSectionId(sectionId);
				assignmentSectionList.remove(0);
				updateSectionList.add(assignmentSection);
			}else{
				AssignmentSection assignmentSection = new AssignmentSection();
				assignmentSection.setAssignmentTaskId(this.getTaskId());
				assignmentSection.setSectionId(sectionId);
				saveSectionList.add(assignmentSection);
			}
		}
		if(!assignmentSectionList.isEmpty()){
			for(AssignmentSection delSection : assignmentSectionList){
				delSectionList.add(delSection);
			}
		}
		
		assignmentTask.setNumOfFile(this.getNumOfFile());
		assignmentTask.setLimitFileSizeKb(this.getLimitFileSizeKb());
		assignmentTask.setMaxScore(this.getMaxScore());
		assignmentTask.setCreateBy(SecurityUtils.getUsername());
		assignmentTask.setCreateDate(new Date());
		assignmentTask.setFlag(true);
		assignmentTask.setIsEvaluateComplete(false);
		validateTask(assignmentTask);
		
		taskService.editTask(assignmentTask, saveSectionList, updateSectionList, delSectionList);
	}
	
	private void validateGetTaskList(){
		if(BeanUtils.isEmpty(this.getCourseId())){
			throw new ParameterNotFoundException("parameter not found");
		}
		
		this.studentTeacherService.validateCourse(SecurityUtils.getUsername(), this.getCourseId());
	}
	
	public List<Object[]> getTaskList(){
		validateGetTaskList();
		return this.taskService.getTaskList(this.getCourseId());
	}
	
	private void validateGetSendList(){
		if(BeanUtils.isEmpty(this.getTaskId())){
			throw new TaskManagementException("parameter not found");
		}
	}
	public List<HashMap<String,Object>> getEvaluatedList(){
		this.validateGetSendList();
		return this.taskService.getEvaluatedList(this.getTaskId());
	}

	public List<HashMap<String,Object>> getSendList() {
		this.validateGetSendList();
		return this.taskService.getSendList(this.getTaskId());
	}

	public AssignmentTask getAndValidateTaskData() {
		this.validateGetSendList();
		AssignmentTask assignmentTask = this.taskService.getTaskData(this.getTaskId());
		
		if(!this.studentTeacherService.validateCourseId(SecurityUtils.getUsername(), assignmentTask.getCourseId())){
			throw new TaskManagementException("dont have permission");
		}
	
		return assignmentTask;
	}
	
	private void validateEvaluateData(){
		if(BeanUtils.isEmpty(this.getWorkId())){
			throw new TaskManagementException("parametere not found");
		}
	}
	
	public AssignmentWork getAndValidateWorkData(){
		this.validateEvaluateData();

		AssignmentWork assignmentWork = this.taskService.getWorkData(this.getWorkId());

		if(!this.studentTeacherService.validateCourseId(SecurityUtils.getUsername(), assignmentWork.getAssignmentTask().getCourseId())){
			throw new TaskManagementException("dont have permission");
		}
	
		return assignmentWork;
	}
	
	public List<HashMap<String,Object>> getFileList(){
		this.validateEvaluateData();
		return this.taskService.getFileList(this.getWorkId());
	}
	
	
	private void validateGetFile(){
		if(BeanUtils.isEmpty(this.getFile())){
			throw new TaskManagementException("parameter not found");
		}
	}
	public AssignmentFile getAndValidateFile(){
		this.validateGetFile();
		AssignmentFile assignmentFile = this.taskService.getFileData(this.getFile());

		if(!this.studentTeacherService.validateCourseId(SecurityUtils.getUsername(),assignmentFile.getAssignmentWork().getAssignmentTask().getCourseId())){
			throw new TaskManagementException("dont have permission");
		}
		
		return assignmentFile;
	}
	
	private void validateEvaluateWork(){
		if(BeanUtils.isEmpty(this.getWorkId())
				|| BeanUtils.isEmpty(this.getTaskId())
				|| BeanUtils.isEmpty(this.getScore())){
			throw new TaskManagementException("parameter not found");
		}
	}

	public void evaluateWork() {
		this.validateEvaluateWork();
		
		AssignmentWork assignmentWork = new AssignmentWork();
		
		assignmentWork.setAssignmentWorkId(this.getWorkId());
		assignmentWork.setScore(this.getScore());
		assignmentWork.setEvaluateDate(new Date());
		assignmentWork.setStatus(1);
		
		this.taskService.updateWork(assignmentWork);
	}

	private void validateEvaluateComplete(){
		if(BeanUtils.isEmpty(this.getTaskId())){
			throw new ParameterNotFoundException("parameter not found");
		}
	}
	
	public void evaluateComplete(TaskCoreGridManager gridManager) {
		this.validateEvaluateComplete();
		gridManager.evaluateComplete(this, SecurityUtils.getUsername());
		
		
	}
	
}
