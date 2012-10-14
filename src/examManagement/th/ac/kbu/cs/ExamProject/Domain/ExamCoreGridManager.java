package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class ExamCoreGridManager extends CoreGridManager<ExamDomain> {

	@Override
	public Object toEntity(ExamDomain domain) {
		Exam exam = new Exam();
		if(BeanUtils.isNotEmpty(domain.getExamId())){
			DetachedCriteria criteria = DetachedCriteria.forClass(Exam.class,"exam");
			criteria.add(Restrictions.eq("exam.examId", domain.getExamId()));
			exam = basicFinderService.findUniqueByCriteria(criteria);
		}
		return exam;
	}

	@Override
	protected void setProjectionList(ProjectionList projectionList,
			ExamDomain domain) {
		projectionList.add(Projections.property("exam.examId"),"examId");
		projectionList.add(Projections.property("exam.examHeader"),"examHeader");
		projectionList.add(Projections.property("exam.startDate"),"startDate");
		projectionList.add(Projections.property("exam.endDate"),"endDate");
		projectionList.add(Projections.property("exam.minQuestion"),"minQuestion");
		projectionList.add(Projections.property("exam.maxQuestion"),"maxQuestion");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		
	}

	@Override
	protected DetachedCriteria initCriteria(ExamDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Exam.class,"exam");
		criteria.createAlias("exam.course", "course");
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
			criteria.addOrder(Order.asc("exam.startDate"));
			criteria.addOrder(Order.asc("exam.endDate"));
		}
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria, ExamDomain domain,String username) {
		criteria.add(Restrictions.in("exam.courseId", this.studentTeacherService.getCourseId(username)));
		if(BeanUtils.isNotEmpty(domain.getCourseId()) && domain.getCourseId() !=0){
			criteria.add(Restrictions.eq("exam.courseId", domain.getCourseId()));
		}
		if(BeanUtils.isNotEmpty(domain.getExamHeader())){
			criteria.add(Restrictions.ilike("exam.examHeader", domain.getExamHeader(),MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.or(
					Restrictions.ge("exam.endDate", new Date())
					,Restrictions.isNull("exam.endDate")));
		criteria.add(Restrictions.eq("exam.flag", true));
	}

	@Override
	public Object toEntityDelete(ExamDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Exam.class,"exam");
		criteria.add(Restrictions.eq("exam.examId",domain.getExamId()));
		
		Exam exam = this.basicFinderService.findUniqueByCriteria(criteria);
		exam.setFlag(false);
		return exam;
	}

}
