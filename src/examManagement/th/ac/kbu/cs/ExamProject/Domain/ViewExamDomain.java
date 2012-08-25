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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamQuestionGroup;
import th.ac.kbu.cs.ExamProject.Entity.ExamSection;
import th.ac.kbu.cs.ExamProject.Entity.QuestionGroup;
import th.ac.kbu.cs.ExamProject.Entity.Section;
import th.ac.kbu.cs.ExamProject.Exception.DataInValidException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.ExamService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class ViewExamDomain extends ExamPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private ExamService examService;
	
	public Exam getExamData(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Exam.class,"exam");
		criteria.createAlias("exam.course", "course");
		criteria.add(Restrictions.eq("exam.examId", this.getExamId()));
		return basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public List<HashMap<String,Object>> getExamQuestionGroupData(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamQuestionGroup.class,"examQuestionGroup");
		criteria.createAlias("examQuestionGroup.questionGroup", "questionGroup");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("examQuestionGroup.ordinal"), "ordinal");
		projectionList.add(Projections.property("examQuestionGroup.examQuestionGroupId"), "examQuestionGroupId");
		projectionList.add(Projections.property("examQuestionGroup.questionPercent"), "questionPercent");
		projectionList.add(Projections.property("examQuestionGroup.secondPerQuestion"), "secondPerQuestion");
		projectionList.add(Projections.property("questionGroup.questionGroupId"), "questionGroupId");
		projectionList.add(Projections.property("questionGroup.questionGroupName"), "questionGroupName");
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("examQuestionGroup.examId", this.getExamId()));
		criteria.addOrder(Order.asc("examQuestionGroup.ordinal"));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
	}

	public List<HashMap<String,Object>> getQuestionGroupData(Long courseId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(QuestionGroup.class,"questionGroup");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("questionGroup.questionGroupId"), "questionGroupId");
		projectionList.add(Projections.property("questionGroup.questionGroupName"), "questionGroupName");
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("questionGroup.courseId", courseId));
		criteria.add(Restrictions.eq("questionGroup.flag", true));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return basicFinderService.findByCriteria(criteria);
	}

	public List<HashMap<String,Object>> getSectionData(Long courseId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Section.class,"section");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("section.sectionId"), "sectionId");
		projectionList.add(Projections.property("section.sectionName"), "sectionName");
		projectionList.add(Projections.property("section.sectionYear"), "sectionYear");
		projectionList.add(Projections.property("section.sectionSemester"), "sectionSemester");
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("section.courseId", courseId));
		criteria.add(Restrictions.eq("section.flag", true));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return basicFinderService.findByCriteria(criteria);
	}
	

	public List<HashMap<String,Object>> getExamSectionData(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamSection.class,"examSection");
		criteria.createAlias("examSection.section", "section");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("examSection.examSectionId"),"examSectionId");
		projectionList.add(Projections.property("section.sectionId"), "sectionId");
		projectionList.add(Projections.property("section.sectionName"), "sectionName");
		projectionList.add(Projections.property("section.sectionYear"), "sectionYear");
		projectionList.add(Projections.property("section.sectionSemester"), "sectionSemester");
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("examSection.examId", this.getExamId()));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return basicFinderService.findByCriteria(criteria);
	}

	public void validateExamData(List<ExamQuestionGroup> examQuestionGroupList,Long examId){
		for(ExamQuestionGroup examQuestionGroup : examQuestionGroupList){
			if(examQuestionGroup.getExamId() != examId){
				throw new DataInValidException("data is invalid");
			}
		}
	}
	
	public List<ExamQuestionGroup> editExamQuestionGroup() throws JsonParseException, JsonMappingException, IOException {
		if(BeanUtils.isEmpty(this.getExamId())){
			throw new ParameterNotFoundException("Parameter not found!!");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		List<ExamQuestionGroup> examQuestionGroupList = null;
		List<ExamQuestionGroup> deletedExamQuestionGroupList = null;
		if(BeanUtils.isNotEmpty(getExamQuestionGroupDeleteStr())){
			deletedExamQuestionGroupList = mapper.readValue(getExamQuestionGroupDeleteStr(), new TypeReference<ArrayList<ExamQuestionGroup>>(){});
		}
		if(BeanUtils.isNotEmpty(getExamQuestionGroupSaveStr())){
			examQuestionGroupList = mapper.readValue(getExamQuestionGroupSaveStr(), new TypeReference<ArrayList<ExamQuestionGroup>>(){});
			validateExamData(examQuestionGroupList,this.getExamId());
		}
		return examService.updateExamQuestionGroup(examQuestionGroupList,deletedExamQuestionGroupList);
	}

	
	
}
