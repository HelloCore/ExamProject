package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.Answer;
import th.ac.kbu.cs.ExamProject.Entity.Question;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.QuestionAnswerService;


@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService{
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	@Transactional
	public Serializable saveQuestion(Question question) {
		return basicEntityService.save(question);
	}

	@Transactional
	public Serializable saveAnswer(Answer answer) {
		return basicEntityService.save(answer);
	}

	@Transactional
	public void updateQuestion(Question question) {
		basicEntityService.update(question);
	}

	@Transactional
	public void updateAnswer(Answer answer) {
		basicEntityService.update(answer);
	}

	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void saveQuestionAnswer(Question question, List<Answer> answers) {
		question.setFlag(true);
		Long questionId = (Long)this.saveQuestion(question);
		for(Answer answer : answers){
			answer.setFlag(true);
			answer.setQuestionId(questionId);
			basicEntityService.save(answer);
		}
	}

}
