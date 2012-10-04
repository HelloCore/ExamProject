package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
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
	
	
	
}
