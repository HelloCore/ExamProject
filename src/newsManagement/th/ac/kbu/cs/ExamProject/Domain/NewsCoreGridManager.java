package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.News;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class NewsCoreGridManager extends CoreGridManager<NewsDomain> {

	@Override
	public Object toEntityDelete(NewsDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class,"news");
		criteria.add(Restrictions.eq("news.newsId", domain.getNewsId()));
		
		News news = this.basicFinderService.findUniqueByCriteria(criteria);
		news.setUsername(SecurityUtils.getUsername());
		news.setUpdateDate(new Date());
		news.setFlag(false);
		return news;
	}

	@Override
	public Object toEntity(NewsDomain domain) {
		return null;
	}

	@Override
	protected void setProjectionList(ProjectionList projectionList,
			NewsDomain domain) {
		projectionList.add(Projections.property("news.newsId"),"newsId");
		projectionList.add(Projections.property("news.updateDate"),"updateDate");
		projectionList.add(Projections.property("news.newsHeader"),"newsHeader");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
	}

	@Override
	protected DetachedCriteria initCriteria(NewsDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class,"news");
		criteria.createAlias("news.course", "course",Criteria.LEFT_JOIN);
		criteria.createAlias("news.user", "user");
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
			criteria.addOrder(Order.desc("updateDate"));
		}
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria, NewsDomain domain,
			String username) {
		criteria.add(Restrictions.eq("news.flag", true));
		
		if(SecurityUtils.getUser().getType()== 2){
			criteria.add(
					Restrictions.or(
							Restrictions.in("news.courseId", this.studentTeacherService.getCourseId(username))
							,Restrictions.isNull("news.courseId")
					)
			);
		}
		if(BeanUtils.isNotEmpty(domain.getCourseCodeSearch())){
			criteria.add(Restrictions.ilike("course.courseCode", domain.getCourseCodeSearch(), MatchMode.ANYWHERE));
		}
		if(BeanUtils.isNotEmpty(domain.getNewsHeaderSearch())){
			criteria.add(Restrictions.ilike("news.newsHeader", domain.getNewsHeaderSearch(),MatchMode.ANYWHERE));
		}
		if(BeanUtils.isNotEmpty(domain.getNewsContentSearch())){
			criteria.add(Restrictions.ilike("news.newsContent", domain.getNewsContentSearch(),MatchMode.ANYWHERE));
		}
	}
}
