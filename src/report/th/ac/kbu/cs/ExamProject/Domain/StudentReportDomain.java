package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;
import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.ExamSection;
import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class StudentReportDomain extends StudentReportPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	private void validateGetStudentTable(){
		if(BeanUtils.isNull(this.getSectionId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	public CoreGrid<HashMap<String, Object>> search(
			StudentReportCoreGridManager gridManager) {
		this.validateGetStudentTable();
		return gridManager.search(this, SecurityUtils.getUsername());
	}
	
	private void validateGetStudentDetail(){
		if(BeanUtils.isNull(this.getCourseId())
				|| BeanUtils.isNull(this.getStudentId())
				|| BeanUtils.isNull(this.getSectionId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public User getStudentData(){
		this.validateGetStudentDetail();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class,"user");
		criteria.add(Restrictions.eq("user.username", this.getStudentId()));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}

	public List<HashMap<String,Object>> getExamData(){
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(ExamSection.class,"examSection");
		subCriteria.setProjection(Projections.property("examSection.examId"));
		subCriteria.add(Restrictions.eq("examSection.sectionId", this.getSectionId()));
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResult.class,"examResult");
		criteria.createAlias("examResult.exam", "exam");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("examResult.examResultId"),"examResultId");
		projectionList.add(Projections.property("examResult.examStartDate"),"examStartDate");
		projectionList.add(Projections.property("examResult.examCompleteDate"),"examCompleteDate");
		projectionList.add(Projections.property("exam.examHeader"),"examHeader");
		projectionList.add(Projections.property("examResult.examCount"),"examCount");
		projectionList.add(Projections.property("examResult.numOfQuestion"),"numOfQuestion");
		projectionList.add(Projections.property("examResult.examScore"),"examScore");
		projectionList.add(Projections.property("examResult.examUsedTime"),"examUsedTime");
		criteria.setProjection(projectionList);
		
		criteria.add(Subqueries.propertyIn("examResult.examId", subCriteria));
		criteria.add(Restrictions.eq("examResult.username", this.getStudentId()));
		criteria.add(Restrictions.eq("examResult.examCompleted",true));
		
		criteria.addOrder(Order.asc("examResult.examStartDate"));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return this.basicFinderService.findByCriteria(criteria);
	}
	public List<HashMap<String,Object>> getEvaluatedList(){
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(AssignmentSection.class,"assignmentSection");
		subCriteria.setProjection(Projections.property("assignmentSection.assignmentTaskId"));
		subCriteria.add(Restrictions.eq("assignmentSection.sectionId", this.getSectionId()));
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentWork.class,"assignmentWork");
		criteria.createAlias("assignmentWork.assignmentTask", "assignmentTask");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentWork.assignmentWorkId"),"assignmentWorkId");
		projectionList.add(Projections.property("assignmentTask.assignmentTaskName"),"assignmentName");
		projectionList.add(Projections.property("assignmentTask.maxScore"),"maxScore");
		projectionList.add(Projections.property("assignmentWork.sendDate"),"sendDate");
		projectionList.add(Projections.property("assignmentWork.score"),"score");
		projectionList.add(Projections.property("assignmentWork.evaluateDate"),"evaluateDate");
		criteria.setProjection(projectionList);
		
		
		criteria.add(Subqueries.propertyIn("assignmentWork.assignmentTaskId", subCriteria));
		criteria.add(Restrictions.eq("assignmentWork.sendBy", this.getStudentId()));
		criteria.add(Restrictions.eq("assignmentWork.status",1));
		
		criteria.addOrder(Order.asc("assignmentWork.sendDate"));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return this.basicFinderService.findByCriteria(criteria);
	}
}
