package th.ac.kbu.cs.ExamProject.Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamQuestionGroup;
import th.ac.kbu.cs.ExamProject.Entity.ExamSection;
import th.ac.kbu.cs.ExamProject.Entity.Question;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.ExamService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class AddExamDomain extends AddExamPrototype {
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	private List<ExamSection> createExamSectionList(String sectionData){
		List<ExamSection> results = null;
		if (this.getIsCalScore()){
			ObjectMapper mapper = new ObjectMapper();
			if(BeanUtils.isNotEmpty(sectionData)){
				try{
					results = mapper.readValue(sectionData, new TypeReference<ArrayList<ExamSection>>(){});
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return results;
	}
	
	private List<ExamQuestionGroup> createExamQuestionGroupList(String questionGroupData){
		ObjectMapper mapper = new ObjectMapper();
		List<ExamQuestionGroup> results = null;
		if(BeanUtils.isNotEmpty(questionGroupData)){
			try{
				results = mapper.readValue(questionGroupData, new TypeReference<ArrayList<ExamQuestionGroup>>(){});
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return results;
	}
	private Exam toEntiy() throws ParseException{
		Exam exam = new Exam();
		SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(BeanUtils.isNotEmpty(this.getExamHeader())){
			exam.setExamHeader(this.getExamHeader());
		}
		if(BeanUtils.isNotEmpty(this.getCourseId())){
			exam.setCourseId(this.getCourseId());
		}
		if(BeanUtils.isNotEmpty(this.getStartDate())){
			exam.setStartDate(parserSDF.parse(this.getStartDate()));
		}
		if(BeanUtils.isNotEmpty(this.getEndDate())){
			exam.setEndDate(parserSDF.parse(this.getEndDate()));
		}
		if(BeanUtils.isNotEmpty(this.getMinQuestion())){
			exam.setMinQuestion(this.getMinQuestion());
		}
		if(BeanUtils.isNotEmpty(this.getMaxQuestion())){
			exam.setMaxQuestion(this.getMaxQuestion());
		}
		if(BeanUtils.isNotEmpty(this.getExamLimit())){
			exam.setExamLimit(this.getExamLimit());
		}
		if(BeanUtils.isNotEmpty(this.getExamSequence())){
			exam.setExamSequence(this.getExamSequence());
		}
		if(BeanUtils.isNotEmpty(this.getIsCalScore())){
			exam.setIsCalScore(this.getIsCalScore());
		}
		if(BeanUtils.isNotEmpty(this.getMaxScore())){
			exam.setMaxScore(this.getMaxScore());
		}
		exam.setFlag(true);
		return exam;
	}
	
	private void validateParameter(){
		if(BeanUtils.isEmpty(this.getExamHeader())
				|| BeanUtils.isEmpty(this.getCourseId())
				|| BeanUtils.isEmpty(this.getMinQuestion())
				|| BeanUtils.isEmpty(this.getMaxQuestion())
				|| BeanUtils.isEmpty(this.getExamLimit())
				|| BeanUtils.isNull(this.getExamSequence())
				|| BeanUtils.isNull(this.getIsCalScore())
				|| BeanUtils.isEmpty(this.getMaxScore())
				|| BeanUtils.isEmpty(this.getQuestionGroupData())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
		if (this.getIsCalScore()){
			if(BeanUtils.isEmpty(this.getSectionData())){
				throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
			}
		}
	}
	
	private void valdiateNumOfQuestion(Exam exam,List<ExamQuestionGroup> examQuestionGroups){
		HashMap<Long,Integer> numOfQuestionList = new HashMap<Long,Integer>();
		
		List<Long> questionGroupIdList = new ArrayList<Long>();
		for(ExamQuestionGroup examQuestionGroup : examQuestionGroups){
			numOfQuestionList.put(examQuestionGroup.getQuestionGroupId(),BeanUtils.toInteger(Math.ceil((examQuestionGroup.getQuestionPercent()/100.0)*exam.getMaxQuestion())+1));
			questionGroupIdList.add(examQuestionGroup.getQuestionGroupId());
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Question.class,"question");
		criteria.createAlias("question.questionGroup", "questionGroup");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("questionGroup.questionGroupId"),"questionGroupId");
		projectionList.add(Projections.groupProperty("questionGroup.questionGroupName"),"questionGroupName");
		projectionList.add(Projections.count("question.questionId"),"questionCount");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.in("question.questionGroupId", questionGroupIdList));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		List<HashMap<String,Object>> results = this.basicFinderService.findByCriteria(criteria);
		Long questionGroupId;
		for (HashMap<String,Object> record : results){
			questionGroupId = BeanUtils.toLong(record.get("questionGroupId"));
			if(numOfQuestionList.get(questionGroupId) > BeanUtils.toInteger(record.get("questionCount"))){
				StringBuilder sb = new StringBuilder();
				sb.append("จำนวนคำถามในบทเรียน ")
					.append(record.get("questionGroupName"))
				.append(" มี ")
					.append(record.get("questionCount").toString())
				.append(" คำถาม ต้องใช้จำนวน")
					.append(numOfQuestionList.get(questionGroupId))
				.append("คำถาม");
				throw new CoreException(new CoreExceptionMessage(sb.toString()));
			}
		}
	}
	
	public void addExam() throws ParseException{
		this.validateParameter();
		Exam exam = this.toEntiy();
		List<ExamSection> examSections = this.createExamSectionList(this.getSectionData());
		List<ExamQuestionGroup> examQuestionGroups = this.createExamQuestionGroupList(this.getQuestionGroupData());
		
		this.valdiateNumOfQuestion(exam, examQuestionGroups);
		examService.addExam(exam, examSections,examQuestionGroups);
	}
}
