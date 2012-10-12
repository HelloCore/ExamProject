package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.AssignmentService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class AssignmentDomain extends AssignmentPrototype{
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private BasicFinderService basicFinderService;

	@Autowired
	private StudentTeacherService studentTeacherService;
	
	public List<Object[]> getAssignmentData(String username) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
					.append(" DISTINCT assignmentTask.assignmentTaskId ")
					.append(" ,assignmentTask.assignmentTaskName ")
					.append(" ,assignmentTask.startDate ")
					.append(" ,assignmentTask.endDate ")
					.append(" ,course.courseCode ")
					.append(" FROM AssignmentSection assignmentSection ")
					.append(" JOIN assignmentSection.assignmentTask assignmentTask ")
					.append(" JOIN assignmentTask.course course ")
					.append(" WHERE assignmentSection.sectionId IN ( ")
						.append(" SELECT studentSection.sectionId ")
						.append(" FROM StudentSection studentSection ")
						.append(" WHERE studentSection.username = ? ")
					.append(") AND assignmentTask.flag = ? ")
					.append(" AND assignmentTask.endDate > ? ")
					.append(" AND (")
						.append(" SELECT COUNT(*) ")
						.append(" FROM AssignmentWork assignmentWork ")
						.append(" WHERE assignmentWork.sendBy = ? ")
						.append(" AND assignmentWork.assignmentTaskId = assignmentTask.assignmentTaskId ")
						.append(" ) = 0 ");
		
		return this.basicFinderService.find(queryString.toString(), new Object[]{username,true,new Date(), username});
	}
	
	public List<Object[]> getAssignmentData(){
		return this.getAssignmentData(SecurityUtils.getUsername());
	}
	
	
	public HashMap<String, Object> getAssignmentDetail(Long assignmentId,
			String username) {

		if(!studentTeacherService.validateAssignmentSection(assignmentId, username)){
			throw new CoreException(CoreExceptionMessage.PERMISSION_DENIED);
		}
		
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
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}

	private void validateGetAssignmentDetail(){
		if(BeanUtils.isEmpty(this.getAssignmentId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	
	public HashMap<String, Object> getAssignmentDetail() {
		this.validateGetAssignmentDetail();
		return this.getAssignmentDetail(this.getAssignmentId(),SecurityUtils.getUsername());
	}

	public CoreGrid<HashMap<String, Object>> getAssignmentResult(
			AssignmentResultCoreGridManager gridManager) {
		return gridManager.search(this, SecurityUtils.getUsername());
	}
	
}
