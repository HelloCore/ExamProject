package th.ac.kbu.cs.ExamProject.Domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.ac.kbu.cs.ExamProject.Entity.Answer;
import th.ac.kbu.cs.ExamProject.Entity.Question;
import th.ac.kbu.cs.ExamProject.Service.QuestionAnswerService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Configurable
public class AddQuestionDomain extends AddQuestionPrototype{
	
	@Autowired
	private QuestionAnswerService questionAnswerService;
	
	public void saveQuestion(){
		questionAnswerService.saveQuestionAnswer(this.toEntity(), this.toAnswer());
	}
	
	public Question toEntity(){
		Question question = new Question();
		if(BeanUtils.isNotEmpty(this.getQuestionText())){
			question.setQuestionText(getQuestionText());
		}
		if(BeanUtils.isNotEmpty(this.getQuestionGroupId())){
			question.setQuestionGroupId(this.getQuestionGroupId());
		}
		return question;
	}
	
	public List<Answer> toAnswer(){
		ObjectMapper mapper = new ObjectMapper();
		List<Answer> results = null;
		if(BeanUtils.isNotEmpty(this.getAnswerStr())){
			try{
				results = mapper.readValue(this.getAnswerStr(), new TypeReference<ArrayList<Answer>>(){});
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
