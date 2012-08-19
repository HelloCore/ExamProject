package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EXAM_RESULT_ANSWER")
public class ExamResultAnswer implements Serializable {

	private static final long serialVersionUID = -5489874895704717923L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "EXAM_RESULT_ANSWER_ID")
	private Long examResultAnswerId;
	
	@Column(name = "EXAM_RESULT_ID")
	private Long examResultId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXAM_RESULT_ID", insertable = false, updatable = false)
	private ExamResult examResult;
	
	@Column(name = "QUESTION_ID")
	private Long questionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_ID", insertable = false, updatable = false)
	private Question question;

	@Column(name = "ANSWER_ID")
	private Long answerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANSWER_ID", insertable = false, updatable = false)
	private Answer answer;

	public Long getExamResultAnswerId() {
		return examResultAnswerId;
	}

	public void setExamResultAnswerId(Long examResultAnswerId) {
		this.examResultAnswerId = examResultAnswerId;
	}

	public Long getExamResultId() {
		return examResultId;
	}

	public void setExamResultId(Long examResultId) {
		this.examResultId = examResultId;
	}

	public ExamResult getExamResult() {
		return examResult;
	}

	public void setExamResult(ExamResult examResult) {
		this.examResult = examResult;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	
}
