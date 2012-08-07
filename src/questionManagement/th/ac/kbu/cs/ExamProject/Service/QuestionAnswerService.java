package th.ac.kbu.cs.ExamProject.Service;

import java.io.Serializable;
import java.util.List;

import th.ac.kbu.cs.ExamProject.Entity.Answer;
import th.ac.kbu.cs.ExamProject.Entity.Question;

public interface QuestionAnswerService {
	Serializable saveQuestion(Question question);
	Serializable saveAnswer(Answer answer);
	void updateQuestion(Question question);
	void updateAnswer(Answer answer);
	void saveQuestionAnswer(Question question,List<Answer> answers);
}
