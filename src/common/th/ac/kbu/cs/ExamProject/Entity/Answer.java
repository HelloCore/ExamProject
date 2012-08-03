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
@Table(name = "ANSWER")
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class Answer implements Serializable{

	private static final long serialVersionUID = -7563240395245338923L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ANSWER_ID")
	private Long answerId;

	@Column(name = "ANSWER_TEXT")
	private String answerText;
	
	@Column(name = "ANSWER_SCORE")
	private Integer answerScore;

	@Column(name = "QUESTION_ID")
	private Long questionId;

	@Column(name = "FLAG")
	private Boolean flag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_ID", insertable = false, updatable = false)
	private Question question;

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public Integer getAnswerScore() {
		return answerScore;
	}

	public void setAnswerScore(Integer answerScore) {
		this.answerScore = answerScore;
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

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
