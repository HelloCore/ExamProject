package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.StudentSection;
import th.ac.kbu.cs.ExamProject.Exception.DataInValidException;
import th.ac.kbu.cs.ExamProject.Service.AssignmentService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;

@Service
public class AssignmentServiceImpl implements AssignmentService{

	@Autowired
	private StudentTeacherService studentTeacherService;
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Override
	public List<HashMap<String, Object>> getAssignmentData(String username) {
		DetachedCriteria subCriteria = DetachedCriteria.forClass(StudentSection.class,"studentSection");
		subCriteria.setProjection(Projections.property("studentSection.sectionId"));
		subCriteria.add(Restrictions.eq("studentSection.username", username));
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentSection.class,"assignmentSection");
		criteria.createAlias("assignmentSection.assignmentTask", "assignmentTask");
		criteria.createAlias("assignmentTask.course", "course");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property("assignmentTask.assignmentTaskId")),"assignmentTaskId");
		projectionList.add(Projections.property("assignmentTask.assignmentTaskName"),"assignmentTaskName");
		projectionList.add(Projections.property("assignmentTask.startDate"),"startDate");
		projectionList.add(Projections.property("assignmentTask.endDate"),"endDate");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("assignmentTask.flag", true));
		criteria.add(Restrictions.ge("assignmentTask.endDate", new Date()));
		criteria.add(Subqueries.propertyIn("assignmentSection.sectionId", subCriteria));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return this.basicFinderService.findByCriteria(criteria);
	}
	
	private void validateAssignmentSection(Long assignmentId,String username){
		DetachedCriteria subCriteria = DetachedCriteria.forClass(StudentSection.class,"studentSection");
		subCriteria.setProjection(Projections.property("studentSection.sectionId"));
		subCriteria.add(Restrictions.eq("studentSection.username", username));
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentSection.class,"assignmentSection");
		criteria.setProjection(Projections.rowCount());
		
		criteria.add(Restrictions.eq("assignmentSection.assignmentTaskId", assignmentId));
		criteria.add(Subqueries.propertyIn("assignmentSection.sectionId", subCriteria));
		
		Long rowCount = this.basicFinderService.findUniqueByCriteria(criteria);
		if(rowCount <= 0L){
			throw new DataInValidException("data invalid");
		}
	}

	@Override
	public HashMap<String, Object> getAssignmentDetail(Long assignmentId,
			String username) {
		validateAssignmentSection(assignmentId,username);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentTask.class,"assignmentTask");
		criteria.createAlias("assignmentTask.course", "course");
		criteria.createAlias("assignmentTask.user", "user");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentTask.assignmentTaskId"),"assignmentId");
		projectionList.add(Projections.property("assignmentTask.assignmentTaskName"),"assignmentName");
		projectionList.add(Projections.property("assignmentTask.assignmentTaskDesc"),"assignmentDesc");
		projectionList.add(Projections.property("assignmentTask.startDate"),"startDate");
		projectionList.add(Projections.property("assignmentTask.endDate"),"endDate");
		projectionList.add(Projections.property("assignmentTask.limitFileSizeKb"),"limitFileSizeKb");
		projectionList.add(Projections.property("assignmentTask.numOfFile"),"numOfFile");
		projectionList.add(Projections.property("assignmentTask.maxScore"),"maxScore");
		projectionList.add(Projections.property("assignmentTask.createDate"),"createDate");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("assignmentTask.assignmentTaskId", assignmentId));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return basicFinderService.findUniqueByCriteria(criteria);
	}

}
