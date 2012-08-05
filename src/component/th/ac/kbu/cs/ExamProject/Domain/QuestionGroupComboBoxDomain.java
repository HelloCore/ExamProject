package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import th.ac.kbu.cs.ExamProject.Entity.QuestionGroup;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class QuestionGroupComboBoxDomain extends ComboBox{
	
	private Long courseId;

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public List<HashMap<String,Object>> getQuestionGroupComboBoxAdmin() {
		DetachedCriteria criteria = DetachedCriteria.forClass(QuestionGroup.class,"questionGroup");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("questionGroup.questionGroupId"),"questionGroupId");
		projectionList.add(Projections.property("questionGroup.questionGroupName"),"questionGroupName");
		criteria.setProjection(projectionList);
		if(BeanUtils.isNotEmpty(this.getCourseId())){
			criteria.add(Restrictions.eq("questionGroup.courseId", this.getCourseId()));
		}
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
	}
	
}
