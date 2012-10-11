package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentFile;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;

	@Override
	@Transactional(noRollbackFor=Exception.class)
	public void assignTask(AssignmentTask assignmentTask, List<Long> sectionIdList) {
		Long assignmentTaskId = (Long)this.basicEntityService.save(assignmentTask);
		List<AssignmentSection> assignmentSectionList = new ArrayList<AssignmentSection>();
		
		for(Long sectionId : sectionIdList){
			AssignmentSection assignmentSection = new AssignmentSection();
			assignmentSection.setAssignmentTaskId(assignmentTaskId);
			assignmentSection.setSectionId(sectionId);
			
			assignmentSectionList.add(assignmentSection);
		}
		this.basicEntityService.save(assignmentSectionList);
	}

	@Override
	public AssignmentTask getTaskData(Long taskId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentTask.class,"assignmentTask");
		criteria.add(Restrictions.eq("assignmentTask.assignmentTaskId", taskId));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public List<HashMap<String,Object>> getSectionData(Long taskId){
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentSection.class,"assignmentSection");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentSection.assignmentSectionId"),"assignmentSectionId");
		projectionList.add(Projections.property("assignmentSection.sectionId"),"sectionId");
		projectionList.add(Projections.property("assignmentSection.assignmentTaskId"),"assignmentTaskId");
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("assignmentSection.assignmentTaskId", taskId));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return this.basicFinderService.findByCriteria(criteria);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void editTask(AssignmentTask assignmentTask,
			List<AssignmentSection> saveSection,
			List<AssignmentSection> updateSection,
			List<AssignmentSection> delSection) {
		this.basicEntityService.deleteAll(delSection);
		this.basicEntityService.update(updateSection);
		this.basicEntityService.save(saveSection);
		
		this.basicEntityService.update(assignmentTask);
	}

	@Override
	public List<Object[]> getTaskList(Long courseId) {
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
		return this.basicFinderService.find(queryString.toString(), courseId);
	}

	@Override
	public List<HashMap<String, Object>> getSendList(Long taskId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentWork.class,"assignmentWork");
		criteria.createAlias("assignmentWork.user", "user");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentWork.assignmentWorkId"),"assignmentWorkId");
		projectionList.add(Projections.property("assignmentWork.sendDate"),"sendDate");
		projectionList.add(Projections.property("user.username"),"studentId");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("assignmentWork.assignmentTaskId", taskId));
		criteria.add(Restrictions.eq("assignmentWork.status",0));
		criteria.addOrder(Order.asc("assignmentWork.sendDate"));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return this.basicFinderService.findByCriteria(criteria);
	}

	@Override
	public List<HashMap<String, Object>> getEvaluatedList(Long taskId) {
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
		
		criteria.add(Restrictions.eq("assignmentWork.assignmentTaskId", taskId));
		criteria.add(Restrictions.eq("assignmentWork.status",1));
		criteria.addOrder(Order.asc("assignmentWork.evaluateDate"));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return this.basicFinderService.findByCriteria(criteria);
	}

	@Override
	public AssignmentWork getWorkData(Long workId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentWork.class,"assignmentWork");
		criteria.createAlias("assignmentWork.assignmentTask", "assignmentTask");
		criteria.createAlias("assignmentWork.user", "user");
		criteria.add(Restrictions.eq("assignmentWork.assignmentWorkId", workId));
		
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}

	@Override
	public List<HashMap<String, Object>> getFileList(Long workId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentFile.class,"assignmentFile");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentFile.assignmentFileId"),"assignmentFileId");
		projectionList.add(Projections.property("assignmentFile.fileName"),"fileName");

		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("assignmentFile.assignmentWorkId", workId));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return this.basicFinderService.findByCriteria(criteria);
	}

	@Override
	public AssignmentFile getFileData(Long fileId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentFile.class,"assignmentFile");
		criteria.createAlias("assignmentFile.assignmentWork", "assignmentWork");
		criteria.createAlias("assignmentWork.assignmentTask", "assignmentTask");

		criteria.add(Restrictions.eq("assignmentFile.assignmentFileId", fileId));
		
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}

	@Override
	public void updateWork(AssignmentWork assignmentWork) {
		this.basicEntityService.update(assignmentWork);
	}
	
	
	
}
