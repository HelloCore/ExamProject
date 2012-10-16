package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class ExamResultReportCoreGridManager extends CoreGridManager<ExamReportDomain>{

	@Override
	public Object toEntityDelete(ExamReportDomain domain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object toEntity(ExamReportDomain domain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setProjectionList(ProjectionList projectionList,
			ExamReportDomain domain) {
		projectionList.add(Projections.property("examResult.examResultId"),"examResultId");
		projectionList.add(Projections.property("examResult.numOfQuestion"),"numOfQuestion");
		projectionList.add(Projections.property("examResult.examCount"),"examCount");
		projectionList.add(Projections.property("examResult.examStartDate"),"examStartDate");
		projectionList.add(Projections.property("examResult.examCompleteDate"),"examCompleteDate");
		projectionList.add(Projections.property("examResult.examUsedTime"),"examUsedTime");
		projectionList.add(Projections.property("examResult.examScore"),"examScore");
		projectionList.add(Projections.property("user.username"),"studentId");
		projectionList.add(Projections.property("prefixName.prefixNameTh"),"prefixNameTh");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
	}

	@Override
	protected DetachedCriteria initCriteria(ExamReportDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResult.class,"examResult");
		criteria.createAlias("examResult.user", "user");
		criteria.createAlias("user.prefixName", "prefixName");
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
			criteria.addOrder(Order.asc("studentId"));
			criteria.addOrder(Order.asc("examCount"));
		}
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria,
			ExamReportDomain domain, String username) {

		criteria.add(Restrictions.eq("examResult.examId", domain.getExamId()));
		criteria.add(Restrictions.eq("examResult.examCompleted", true));
		
	}

}
