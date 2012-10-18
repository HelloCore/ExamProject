package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import th.ac.kbu.cs.ExamProject.Entity.TeacherCourse;
import th.ac.kbu.cs.ExamProject.Entity.User;

public class TeacherComboBoxDomain extends ComboBox{
	
	private Long courseId;

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public List<HashMap<String,Object>> getTeacherComboBox() {
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		subCriteria.setProjection(Projections.property("teacherCourse.username"));
		subCriteria.add(Restrictions.eq("teacherCourse.courseId", this.getCourseId()));
		
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class,"user");
		criteria.createAlias("user.prefixName", "prefixName");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("user.username"),"username");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
		projectionList.add(Projections.property("prefixName.prefixNameTh"),"prefixNameTh");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("user.type",2));
		criteria.add(Subqueries.propertyNotIn("user.username", subCriteria));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
	}
	
}
