package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGridManager;
import th.ac.kbu.cs.ExamProject.Entity.Section;
import th.ac.kbu.cs.ExamProject.Entity.TeacherCourse;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class SectionCoreGridManager extends CoreGridManager<SectionDomain>{
	
	@Override
	protected void setProjectionList(ProjectionList projectionList,
			SectionDomain domain) {
		projectionList.add(Projections.property("section.sectionId"),"sectionId");
		projectionList.add(Projections.property("section.sectionName"),"sectionName");
		projectionList.add(Projections.property("section.sectionYear"),"sectionYear");
		projectionList.add(Projections.property("section.sectionSemester"),"sectionSemester");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
	}

	@Override
	protected DetachedCriteria initCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Section.class,"section");
		criteria.createAlias("section.course", "course");
		return criteria;
	}

	@Override
	protected DetachedCriteria initCriteriaTeacher() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		criteria.createAlias("teacherCourse.course", "course");
		criteria.createAlias("teacherCourse.user", "user");
		criteria.createAlias("course.section", "section");
		return criteria;
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria, SectionDomain domain) {
		// TODO Auto-generated method stub
		if(BeanUtils.isNotEmpty(domain.getSectionNameSearch())){
			criteria.add(Restrictions.ilike("section.sectionName", domain.getSectionNameSearch(), MatchMode.ANYWHERE));
		}
		if(BeanUtils.isNotEmpty(domain.getSectionSemesterSearch())){
			criteria.add(Restrictions.eq("section.sectionSemester", BeanUtils.toInteger(domain.getSectionSemesterSearch())));
		}
		if(BeanUtils.isNotEmpty(domain.getSectionYearSearch())){
			criteria.add(Restrictions.eq("section.sectionYear", BeanUtils.toInteger(domain.getSectionYearSearch())));
		}
		if(BeanUtils.isNotEmpty(domain.getCourseCodeSearch())){
			criteria.add(Restrictions.ilike("course.courseCode", domain.getCourseCodeSearch(), MatchMode.ANYWHERE));
		}
	}

	@Override
	protected void addOrder(DetachedCriteria criteria) {
		if(BeanUtils.isNotNull(getOrder()) && BeanUtils.isNotNull(getOrderBy())){
			if(getOrder().equalsIgnoreCase("asc")){
				criteria.addOrder(Order.asc(getOrderBy()));
			}else{
				criteria.addOrder(Order.desc(getOrderBy()));
			}
		}
	}

	@Override
	public Object toEntity(SectionDomain domain) {
		Section section = new Section();
		if(BeanUtils.isNotNull(domain.getSectionId())){
			section.setSectionId(domain.getSectionId());
		}

		if(BeanUtils.isNotNull(domain.getSectionName())){
			section.setSectionName(domain.getSectionName());
		}

		if(BeanUtils.isNotNull(domain.getSectionSemester())){
			section.setSectionSemester(domain.getSectionSemester());
		}

		if(BeanUtils.isNotNull(domain.getSectionYear())){
			section.setSectionYear(domain.getSectionYear());
		}

		if(BeanUtils.isNotNull(domain.getCourseId())){
			section.setCourseId(domain.getCourseId());
		}
		return section;
	}

}
