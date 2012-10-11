package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class TaskCoreGridManager extends CoreGridManager<TaskDomain>{

	@Override
	public Object toEntityDelete(TaskDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentTask.class,"assignmentTask");
		criteria.add(Restrictions.eq("assignmentTask.assignmentTaskId",domain.getTaskId()));
		
		AssignmentTask assignmentTask = this.basicFinderService.findUniqueByCriteria(criteria);
		assignmentTask.setFlag(false);
		return assignmentTask;
	}

	@Override
	public Object toEntity(TaskDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentTask.class,"assignmentTask");
		criteria.add(Restrictions.eq("assignmentTask.assignmentTaskId",domain.getTaskId()));
		
		AssignmentTask assignmentTask = this.basicFinderService.findUniqueByCriteria(criteria);
		assignmentTask.setIsEvaluateComplete(true);
		return assignmentTask;
	}

	@Override
	protected void setProjectionList(ProjectionList projectionList,
			TaskDomain domain) {
		projectionList.add(Projections.property("assignmentTask.assignmentTaskId"),"taskId");
		projectionList.add(Projections.property("assignmentTask.assignmentTaskName"),"taskName");
		projectionList.add(Projections.property("assignmentTask.assignmentTaskDesc"),"taskDesc");
		projectionList.add(Projections.property("assignmentTask.startDate"),"startDate");
		projectionList.add(Projections.property("assignmentTask.endDate"),"endDate");
		projectionList.add(Projections.property("assignmentTask.limitFileSizeKb"),"limitFileSizeKb");
		projectionList.add(Projections.property("assignmentTask.numOfFile"),"numOfFile");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		projectionList.add(Projections.property("assignmentTask.maxScore"),"maxScore");
		projectionList.add(Projections.property("assignmentTask.createDate"),"createDate");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
	}

	@Override
	protected DetachedCriteria initCriteria(TaskDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentTask.class,"assignmentTask");
		criteria.createAlias("assignmentTask.user", "user");
		criteria.createAlias("assignmentTask.course", "course");
		return criteria;
	}

	@Override
	protected void addOrder(DetachedCriteria criteria) {
		criteria.addOrder(Order.asc("assignmentTask.startDate"));
		criteria.addOrder(Order.asc("assignmentTask.endDate"));
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria, TaskDomain domain,
			String username) {
		criteria.add(Restrictions.in("assignmentTask.courseId", this.studentTeacherService.getCourseId(username)));
		
		if(BeanUtils.isNotEmpty(domain.getCourseId()) && domain.getCourseId() !=0){
			criteria.add(Restrictions.eq("assignmentTask.courseId", domain.getCourseId()));
		}
		if(BeanUtils.isNotEmpty(domain.getTaskName())){
			criteria.add(Restrictions.ilike("assignmentTask.assignmentTaskName", domain.getTaskName(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("assignmentTask.flag", true));
//		criteria.add(Restrictions.ge("assignmentTask.endDate", new Date()));
		
		if(BeanUtils.isNotNull(domain.getIsComplete())){
			criteria.add(Restrictions.eq("assignmentTask.isEvaluateComplete", false));
		}
	}
	
	public void evaluateComplete(TaskDomain domain,String username){
		this.basicEntityService.update(this.toEntity(domain));
	}

}
