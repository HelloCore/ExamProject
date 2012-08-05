package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import th.ac.kbu.cs.ExamProject.Entity.Course;

public class CourseComboBoxDomain extends ComboBox{

	public List<HashMap<String,Object>> getCourseComboBoxAdmin(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Course.class,"course");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("course.courseId"),"courseId");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		criteria.setProjection(projectionList);
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
	}

}
