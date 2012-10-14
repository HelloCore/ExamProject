package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.News;
import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class ViewNewsDomain extends NewsPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired 
	private BasicEntityService basicEntityService;
	
	@Autowired
	private StudentTeacherService studentTeacherService;
	
	public List<HashMap<String,Object>> getCourseData(){
		List<HashMap<String,Object>> results = null;
		if(SecurityUtils.getUser().getType().equals(2)){
			results = this.studentTeacherService.getCourseData(SecurityUtils.getUsername());
		}
		return results;
	}
	
	public News getNews(Long newsId){
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class,"news");
		criteria.add(Restrictions.eq("news.newsId", newsId));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}

	private void validateData(){
		if(BeanUtils.isEmpty(this.getNewsId())
				|| BeanUtils.isEmpty(this.getNewsHeader())
				|| BeanUtils.isEmpty(this.getNewsContent())
				|| BeanUtils.isEmpty(this.getCourseId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public void editNews() {
		this.validateData();
		News news = new News();
		news.setNewsId(this.getNewsId());
		if(!this.getCourseId().equals(0L)){
			news.setCourseId(this.getCourseId());
		}
		news.setNewsHeader(this.getNewsHeader());
		news.setNewsContent(this.getNewsContent());
		news.setUpdateDate(new Date());
		news.setUsername(SecurityUtils.getUsername());
		news.setFlag(true);
		basicEntityService.update(news);
	}
	
	public List<HashMap<String,Object>> getNewsList(Integer start,Integer limit){
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class,"news");
		criteria.createAlias("news.course", "course",Criteria.LEFT_JOIN);
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("news.newsId"),"id");
		projectionList.add(Projections.property("news.newsHeader"),"newsHeader");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		
		criteria.setProjection(projectionList);
		
		User user = SecurityUtils.getUser();
		if(BeanUtils.isNotNull(user)){
			if(user.getType().equals(2)){
				List<Long> courseIdList = this.studentTeacherService.getCourseId(user.getUsername());
				if(BeanUtils.isEmpty(courseIdList)){
					criteria.add(Restrictions.isNull("news.courseId"));
				}else{
					criteria.add(
							Restrictions.or(
									Restrictions.in("news.courseId",courseIdList)
									,Restrictions.isNull("news.courseId")
								)
					);
				}
			}else if (user.getType().equals(3)){
				List<Long> courseIdList = this.studentTeacherService.getStudentCourseId(user.getUsername());
				if(BeanUtils.isEmpty(courseIdList)){
					criteria.add(Restrictions.isNull("news.courseId"));
				}else{
					criteria.add(
						Restrictions.or(
								Restrictions.in("news.courseId",courseIdList)
								,Restrictions.isNull("news.courseId")
						)
					);
				}
			}else{
				criteria.add(Restrictions.isNull("news.courseId"));
			}
		}else{
			criteria.add(Restrictions.isNull("news.courseId"));
		}
		
		criteria.add(Restrictions.eq("news.flag", true));
		criteria.addOrder(Order.desc("news.updateDate"));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return basicFinderService.findByCriteria(criteria, start, limit);
	}
	private News getNewsItem(){
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class,"news");
		criteria.createAlias("news.user", "user");
		criteria.add(Restrictions.eq("news.newsId", this.getId()));
		
		User user = SecurityUtils.getUser();
		if(BeanUtils.isNotNull(user)){
			if(user.getType().equals(2)){
				List<Long> courseIdList = this.studentTeacherService.getCourseId(user.getUsername());
				if(BeanUtils.isEmpty(courseIdList)){
					criteria.add(Restrictions.isNull("news.courseId"));
				}else{
					criteria.add(
							Restrictions.or(
									Restrictions.in("news.courseId",courseIdList)
									,Restrictions.isNull("news.courseId")
								)
					);
				}
			}else if (user.getType().equals(3)){
				List<Long> courseIdList = this.studentTeacherService.getStudentCourseId(user.getUsername());
				if(BeanUtils.isEmpty(courseIdList)){
					criteria.add(Restrictions.isNull("news.courseId"));
				}else{
					criteria.add(
							Restrictions.or(
									Restrictions.in("news.courseId",courseIdList)
									,Restrictions.isNull("news.courseId")
							)
					);
				}
			}else{
				criteria.add(Restrictions.isNull("news.courseId"));
			}
		}else{
			criteria.add(Restrictions.isNull("news.courseId"));
		}
		return basicFinderService.findUniqueByCriteria(criteria);
	}
	public News getNewsInfo(){
		News news = null;
		if(BeanUtils.isNotNull(this.getId())){
			news = this.getNewsItem();
		}
		return news;
	}

	public Long getNewsCount() {
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class,"news");
		
		
		criteria.setProjection(Projections.rowCount());
		
		User user = SecurityUtils.getUser();
		if(BeanUtils.isNotNull(user)){
			if(user.getType().equals(2)){
				List<Long> courseIdList = this.studentTeacherService.getCourseId(user.getUsername());
				if(BeanUtils.isEmpty(courseIdList)){
					criteria.add(Restrictions.isNull("news.courseId"));
				}else{
					criteria.add(
						Restrictions.or(
								Restrictions.in("news.courseId",courseIdList)
								,Restrictions.isNull("news.courseId")
						)
					);
				}
			}else if (user.getType().equals(3)){
				List<Long> courseIdList = this.studentTeacherService.getStudentCourseId(user.getUsername());
				if(BeanUtils.isEmpty(courseIdList)){
					criteria.add(Restrictions.isNull("news.courseId"));
				}else{
					criteria.add(
							Restrictions.or(
									Restrictions.in("news.courseId",courseIdList)
									,Restrictions.isNull("news.courseId")
							)
					);
				}
			}else{
				criteria.add(Restrictions.isNull("news.courseId"));
			}
		}else{
			criteria.add(Restrictions.isNull("news.courseId"));
		}
		criteria.add(Restrictions.eq("news.flag", true));

		return basicFinderService.findUniqueByCriteria(criteria);
	}
}
