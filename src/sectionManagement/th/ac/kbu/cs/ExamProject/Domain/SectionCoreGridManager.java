package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

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
		projectionList.add(Projections.property("masterSection.masterSectionId"),"masterSectionId");
		projectionList.add(Projections.property("masterSection.sectionYear"),"sectionYear");
		projectionList.add(Projections.property("masterSection.sectionSemester"),"sectionSemester");
		projectionList.add(Projections.property("masterSection.status"),"status");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
	}

	@Override
	protected DetachedCriteria initCriteria(SectionDomain domain) {
	
		DetachedCriteria criteria = DetachedCriteria.forClass(Section.class,"section");
		criteria.createAlias("section.course", "course");
		criteria.createAlias("section.masterSection", "masterSection");
		
		return criteria;
	}

	@Override
	protected void applyCriteria(DetachedCriteria criteria, SectionDomain domain,String username) {
		if(BeanUtils.isNotEmpty(domain.getSectionNameSearch())){
			criteria.add(Restrictions.ilike("section.sectionName", domain.getSectionNameSearch(), MatchMode.ANYWHERE));
		}
		if(BeanUtils.isNotEmpty(domain.getSectionSemesterSearch())){
			criteria.add(Restrictions.eq("masterSection.sectionSemester", BeanUtils.toInteger(domain.getSectionSemesterSearch())));
		}
		if(BeanUtils.isNotEmpty(domain.getSectionYearSearch())){
			criteria.add(Restrictions.eq("masterSection.sectionYear", BeanUtils.toInteger(domain.getSectionYearSearch())));
		}
		if(BeanUtils.isNotEmpty(domain.getCourseCodeSearch())){
			criteria.add(Restrictions.ilike("course.courseCode", domain.getCourseCodeSearch(), MatchMode.ANYWHERE));
		}
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		subCriteria.setProjection(Projections.property("teacherCourse.courseId"));
		subCriteria.add(Restrictions.eq("teacherCourse.username", username));
		
		criteria.add(Subqueries.propertyIn("section.courseId", subCriteria));
		criteria.add(Restrictions.eq("section.flag",true));
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
			criteria.addOrder(Order.desc("status"));
			criteria.addOrder(Order.asc("courseCode"));
			criteria.addOrder(Order.desc("sectionYear"));
			criteria.addOrder(Order.asc("sectionSemester"));
			criteria.addOrder(Order.asc("sectionName"));
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

		if(BeanUtils.isNotNull(domain.getMasterSectionId())){
			section.setMasterSectionId(domain.getMasterSectionId());
		}

		if(BeanUtils.isNotNull(domain.getCourseId())){
			section.setCourseId(domain.getCourseId());
		}
		section.setFlag(true);
		return section;
	}
	

	@Override
	public Object toEntityDelete(SectionDomain domain) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Section.class,"section");
		criteria.add(Restrictions.eq("section.sectionId",domain.getSectionId()));
		
		Section section = this.basicFinderService.findUniqueByCriteria(criteria);
		section.setFlag(false);
		return section;
	}






}
