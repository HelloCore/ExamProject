package th.ac.kbu.cs.ExamProject.Domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.PathDescription;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentFile;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Exception.CoreRedirectException;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
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
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	private AssignmentTask getTaskData(Long taskId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentTask.class,"assignmentTask");
		criteria.add(Restrictions.eq("assignmentTask.assignmentTaskId", taskId));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
	private AssignmentWork getWorkData(Long workId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentWork.class,"assignmentWork");
		criteria.createAlias("assignmentWork.assignmentTask", "assignmentTask");
		criteria.createAlias("assignmentWork.user", "user");
		criteria.add(Restrictions.eq("assignmentWork.assignmentWorkId", workId));
		
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}

	private AssignmentFile getFileData(Long fileId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentFile.class,"assignmentFile");
		criteria.createAlias("assignmentFile.assignmentWork", "assignmentWork");
		criteria.createAlias("assignmentWork.assignmentTask", "assignmentTask");

		criteria.add(Restrictions.eq("assignmentFile.assignmentFileId", fileId));
		
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
	private void validateAssignTaskData(){
		if(BeanUtils.isEmpty(this.getCourseId())
				|| BeanUtils.isEmpty(this.getSectionIdStr())
				|| BeanUtils.isEmpty(this.getTaskName())
				|| BeanUtils.isEmpty(this.getTaskDesc())
				|| BeanUtils.isEmpty(this.getStartDate())
				|| BeanUtils.isEmpty(this.getNumOfFile())
				|| BeanUtils.isEmpty(this.getLimitFileSizeKb())
				|| BeanUtils.isEmpty(this.getMaxScore())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	private void validateTask(AssignmentTask assignmentTask){
		if(assignmentTask.getNumOfFile()>10 || assignmentTask.getNumOfFile() < 1){
			throw new CoreException(CoreExceptionMessage.INVALID_DATA);
		}
		
		if(assignmentTask.getLimitFileSizeKb()> 204800L ||  assignmentTask.getLimitFileSizeKb() < 1){
			throw new CoreException(CoreExceptionMessage.INVALID_DATA);
		}
		
		if(assignmentTask.getMaxScore() < 1 || assignmentTask.getMaxScore() > 10000){
			throw new CoreException(CoreExceptionMessage.INVALID_DATA);
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
			throw new CoreException(new CoreExceptionMessage(e.getMessage()));
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
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}

	public AssignmentTask getTaskData() {
		this.validateTaskData();
		return this.getTaskData(this.getTaskId());
	}

	public List<HashMap<String,Object>> getSectionData() {
		this.validateTaskData();
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentSection.class,"assignmentSection");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentSection.assignmentSectionId"),"assignmentSectionId");
		projectionList.add(Projections.property("assignmentSection.sectionId"),"sectionId");
		projectionList.add(Projections.property("assignmentSection.assignmentTaskId"),"assignmentTaskId");
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("assignmentSection.assignmentTaskId", this.getTaskId()));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return this.basicFinderService.findByCriteria(criteria);
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
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
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
			throw new CoreException(new CoreExceptionMessage(e.getMessage()));
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
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
		
		this.studentTeacherService.validateCourse(SecurityUtils.getUsername(), this.getCourseId());
	}
	
	public List<Object[]> getTaskList(){
		validateGetTaskList();
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT ")
					.append(" assignmentTask.assignmentTaskId ")
					.append(" ,course.courseCode")
					.append(" ,assignmentTask.assignmentTaskName ")
					.append(" ,assignmentTask.endDate ")
					.append(" ,( SELECT COUNT(*) FROM AssignmentWork assignmentWork1 ")
						.append(" WHERE assignmentWork1.assignmentTaskId = assignmentTask.assignmentTaskId )")
					.append(" ,( SELECT COUNT(*) FROM AssignmentWork assignmentWork2 ")
						.append(" WHERE assignmentWork2.assignmentTaskId = assignmentTask.assignmentTaskId ")
						.append(" AND assignmentWork2.status = 1) ")
					.append(" FROM AssignmentTask assignmentTask ")
					.append(" JOIN assignmentTask.course course ")
					.append(" WHERE assignmentTask.courseId = ? ")
					.append(" AND assignmentTask.flag = 1 ")
					.append(" AND assignmentTask.isEvaluateComplete = 0 ")
					.append(" ORDER BY assignmentTask.endDate asc ");
		
		return this.basicFinderService.find(queryString.toString(), this.getCourseId());
	}
	
	private void validateGetSendList(){
		if(BeanUtils.isEmpty(this.getTaskId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	public List<HashMap<String,Object>> getEvaluatedList(){
		this.validateGetSendList();
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentWork.class,"assignmentWork");
		criteria.createAlias("assignmentWork.user", "user");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentWork.assignmentWorkId"),"assignmentWorkId");
		projectionList.add(Projections.property("assignmentWork.sendDate"),"sendDate");
		projectionList.add(Projections.property("user.username"),"studentId");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
		projectionList.add(Projections.property("assignmentWork.score"),"score");
		projectionList.add(Projections.property("assignmentWork.evaluateDate"),"evaluateDate");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("assignmentWork.assignmentTaskId", this.getTaskId()));
		criteria.add(Restrictions.eq("assignmentWork.status",1));
		criteria.addOrder(Order.asc("assignmentWork.evaluateDate"));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return this.basicFinderService.findByCriteria(criteria);
	}

	public List<HashMap<String,Object>> getSendList() {
		this.validateGetSendList();
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentWork.class,"assignmentWork");
		criteria.createAlias("assignmentWork.user", "user");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentWork.assignmentWorkId"),"assignmentWorkId");
		projectionList.add(Projections.property("assignmentWork.sendDate"),"sendDate");
		projectionList.add(Projections.property("user.username"),"studentId");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("assignmentWork.assignmentTaskId", this.getTaskId()));
		criteria.add(Restrictions.eq("assignmentWork.status",0));
		criteria.addOrder(Order.asc("assignmentWork.sendDate"));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return this.basicFinderService.findByCriteria(criteria);
	}

	public AssignmentTask getAndValidateTaskData() {
		this.validateGetSendList();
		AssignmentTask assignmentTask = this.getTaskData(this.getTaskId());
		
		if(!this.studentTeacherService.validateCourseId(SecurityUtils.getUsername(), assignmentTask.getCourseId())){
			throw new CoreRedirectException(CoreExceptionMessage.PERMISSION_DENIED, PathDescription.Management.Task.TASKLIST);
		}
	
		return assignmentTask;
	}
	
	private void validateEvaluateData(){
		if(BeanUtils.isEmpty(this.getWorkId())){
			throw new CoreRedirectException(CoreExceptionMessage.PARAMETER_NOT_FOUND, PathDescription.Management.Task.TASKLIST);
		}
	}
	
	public AssignmentWork getAndValidateWorkData(){
		this.validateEvaluateData();

		AssignmentWork assignmentWork = this.getWorkData(this.getWorkId());

		if(!this.studentTeacherService.validateCourseId(SecurityUtils.getUsername(), assignmentWork.getAssignmentTask().getCourseId())){
			throw new CoreRedirectException(CoreExceptionMessage.PERMISSION_DENIED, PathDescription.Management.Task.TASKLIST);
		}
	
		return assignmentWork;
	}
	
	public List<HashMap<String,Object>> getFileList(){
		this.validateEvaluateData();
		
	DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentFile.class,"assignmentFile");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentFile.assignmentFileId"),"assignmentFileId");
		projectionList.add(Projections.property("assignmentFile.fileName"),"fileName");

		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("assignmentFile.assignmentWorkId", this.getWorkId()));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return this.basicFinderService.findByCriteria(criteria);
	}
	
	
	private void validateGetFile(){
		if(BeanUtils.isEmpty(this.getFile())){
			throw new CoreRedirectException(CoreExceptionMessage.PARAMETER_NOT_FOUND, PathDescription.Management.Task.TASKLIST);
		}
	}
	public AssignmentFile getAndValidateFile(){
		this.validateGetFile();
		AssignmentFile assignmentFile = this.getFileData(this.getFile());

		if(!this.studentTeacherService.validateCourseId(SecurityUtils.getUsername(),assignmentFile.getAssignmentWork().getAssignmentTask().getCourseId())){
			throw new CoreRedirectException(CoreExceptionMessage.PERMISSION_DENIED, PathDescription.Management.Task.TASKLIST);
		}
		
		return assignmentFile;
	}
	
	private void validateEvaluateWork(){
		if(BeanUtils.isEmpty(this.getWorkId())
				|| BeanUtils.isEmpty(this.getTaskId())
				|| BeanUtils.isEmpty(this.getScore())){
			throw new CoreRedirectException(CoreExceptionMessage.PARAMETER_NOT_FOUND, PathDescription.Management.Task.TASKLIST);
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
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public void evaluateComplete(TaskCoreGridManager gridManager) {
		this.validateEvaluateComplete();
		gridManager.evaluateComplete(this, SecurityUtils.getUsername());
		
		
	}
	
}
