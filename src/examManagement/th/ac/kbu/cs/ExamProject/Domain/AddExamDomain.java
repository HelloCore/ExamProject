package th.ac.kbu.cs.ExamProject.Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamQuestionGroup;
import th.ac.kbu.cs.ExamProject.Entity.ExamSection;
import th.ac.kbu.cs.ExamProject.Service.ExamService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class AddExamDomain extends AddExamPrototype {
	
	@Autowired
	private ExamService examService;
	
	private List<ExamSection> createExamSectionList(String sectionData){
		ObjectMapper mapper = new ObjectMapper();
		List<ExamSection> results = null;

		if(BeanUtils.isNotEmpty(sectionData)){
			try{
				results = mapper.readValue(sectionData, new TypeReference<ArrayList<ExamSection>>(){});
			}catch(Exception e){
				e.printStackTrace();
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
		System.out.println("Domain2");
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
		exam.setExamCount(0);
		exam.setFlag(true);
		return exam;
	}
	
	public void addExam() throws ParseException{
		examService.addExam(this.toEntiy(), createExamSectionList(this.getSectionData()), createExamQuestionGroupList(this.getQuestionGroupData()));
	}
}
