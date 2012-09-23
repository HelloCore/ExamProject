package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.Entity.Section;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class SectionComboBoxDomain extends ComboBox{
	
	private Long courseId;

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public List<HashMap<String,Object>> getSectionComboBox() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Section.class,"section");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("section.sectionId"),"sectionId");
		projectionList.add(Projections.property("section.sectionName"),"sectionName");
		projectionList.add(Projections.property("section.sectionYear"),"sectionYear");
		projectionList.add(Projections.property("section.sectionSemester"),"sectionSemester");
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("section.flag",true));
		criteria.add(Restrictions.eq("section.status", 1));
		criteria.add(Restrictions.in("section.courseId", this.teacherService.getCourseId(SecurityUtils.getUsername())));
		if(BeanUtils.isNotEmpty(this.getCourseId())){
			criteria.add(Restrictions.eq("section.courseId", this.getCourseId()));
		}
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
	}
	
}
