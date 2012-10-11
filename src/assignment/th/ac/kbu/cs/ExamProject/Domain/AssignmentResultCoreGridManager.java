package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class AssignmentResultCoreGridManager extends CoreGridManager<AssignmentDomain> {

	@Override
	public Object toEntityDelete(AssignmentDomain domain) {
		return null;
	} 

	@Override
	public Object toEntity(AssignmentDomain domain) {
		return null;
	}

	@Override
	protected void setProjectionList(ProjectionList projectionList,
			AssignmentDomain domain) {
		projectionList.add(Projections.property("assignmentWork.sendDate"),"sendDate");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		projectionList.add(Projections.property("assignmentTask.assignmentTaskName"),"assignmentTaskName");
		projectionList.add(Projections.property("assignmentWork.status"),"status");
		projectionList.add(Projections.property("assignmentWork.score"),"score");
		projectionList.add(Projections.property("assignmentTask.maxScore"),"maxScore");
		projectionList.add(Projections.property("assignmentWork.evaluateDate"),"evaluateDate");
	}

	@Override
	protected DetachedCriteria initCriteria(AssignmentDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentWork.class,"assignmentWork");
		criteria.createAlias("assignmentWork.assignmentTask", "assignmentTask");
		criteria.createAlias("assignmentTask.course", "course");
		return criteria;
	}

	@Override
	protected void addOrder(DetachedCriteria criteria) {
		if(BeanUtils.isNotEmpty(getOrder()) && BeanUtils.isNotEmpty(getOrderBy())){
			if(getOrder().equalsIgnoreCase("asc")){
				criteria.addOrder(Order.asc(getOrderBy()));
			}else{
				criteria.addOrder(Order.desc(getOrderBy()));
			}
		}else{
			criteria.addOrder(Order.desc("assignmentWork.status"));
			criteria.addOrder(Order.desc("assignmentWork.sendDate"));
		}
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria,
			AssignmentDomain domain, String username) {
		criteria.add(Restrictions.eq("assignmentWork.sendBy", username));
	}

}
