package th.ac.kbu.cs.ExamProject.Domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.ac.kbu.cs.ExamProject.Entity.QuestionGroup;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class QuestionGroupComboBoxDomain extends ComboBox{
	private String questionGroupArray;
	
	public String getQuestionGroupArray() {
		return questionGroupArray;
	}

	public void setQuestionGroupArray(String questionGroupArray) {
		this.questionGroupArray = questionGroupArray;
	}

	private Long courseId;

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public List<HashMap<String,Object>> getQuestionGroupComboBox() throws JsonParseException, JsonMappingException, IOException {
		DetachedCriteria criteria = DetachedCriteria.forClass(QuestionGroup.class,"questionGroup");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("questionGroup.questionGroupId"),"questionGroupId");
		projectionList.add(Projections.property("questionGroup.questionGroupName"),"questionGroupName");
		criteria.setProjection(projectionList);
		criteria.addOrder(Order.asc("questionGroup.questionGroupName"));
		criteria.add(Restrictions.in("questionGroup.courseId",this.teacherService.getCourseId(SecurityUtils.getUsername())));
		if(BeanUtils.isNotEmpty(this.getCourseId())){
			criteria.add(Restrictions.eq("questionGroup.courseId", this.getCourseId()));
		}
		if(BeanUtils.isNotEmpty(this.getQuestionGroupArray())){
			ObjectMapper objectMapper = new ObjectMapper();
			
			List<Long> questionGroupList = objectMapper.readValue(questionGroupArray, new TypeReference<ArrayList<Long>>(){});
			
			criteria.add(Restrictions.not(Restrictions.in("questionGroup.questionGroupId", questionGroupList)));
		}
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
	}
	
}
