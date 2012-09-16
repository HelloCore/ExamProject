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
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class ExamResultAnswer implements Serializable {

	private static final long serialVersionUID = -5489874895704717923L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "EXAM_RESULT_ANSWER_ID")
	private Long examResultAnswerId;
	
	@Column(name = "EXAM_RESULT_ID" ,updatable = false)
	private Long examResultId;
	
	@Column(name = "ORDINAL",updatable = false)
	private Integer ordinal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXAM_RESULT_ID", insertable = false, updatable = false)
	private ExamResult examResult;
	
	@Column(name = "QUESTION_ID",updatable=false)
	private Long questionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_ID", insertable = false, updatable = false)
	private Question question;

	@Column(name = "ANSWER_ID")
	private Long answerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANSWER_ID", insertable = false, updatable = false)
	private Answer answer;
	
	@Column(name = "ANSWER_ID_1",updatable = false)
	private Long answerId1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANSWER_ID_1", insertable = false, updatable = false)
	private Answer answer1;
	
	@Column(name = "ANSWER_ID_2",updatable = false)
	private Long answerId2;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANSWER_ID_2", insertable = false, updatable = false)
	private Answer answer2;
	
	@Column(name = "ANSWER_ID_3",updatable = false)
	private Long answerId3;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANSWER_ID_3", insertable = false, updatable = false)
	private Answer answer3;
	
	@Column(name = "ANSWER_ID_4",updatable = false)
	private Long answerId4;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANSWER_ID_4", insertable = false, updatable = false)
	private Answer answer4;
	
	public Long getAnswerId1() {
		return answerId1;
	}

	public void setAnswerId1(Long answerId1) {
		this.answerId1 = answerId1;
	}

	public Answer getAnswer1() {
		return answer1;
	}

	public void setAnswer1(Answer answer1) {
		this.answer1 = answer1;
	}

	public Long getAnswerId2() {
		return answerId2;
	}

	public void setAnswerId2(Long answerId2) {
		this.answerId2 = answerId2;
	}

	public Answer getAnswer2() {
		return answer2;
	}

	public void setAnswer2(Answer answer2) {
		this.answer2 = answer2;
	}

	public Long getAnswerId3() {
		return answerId3;
	}

	public void setAnswerId3(Long answerId3) {
		this.answerId3 = answerId3;
	}

	public Answer getAnswer3() {
		return answer3;
	}

	public void setAnswer3(Answer answer3) {
		this.answer3 = answer3;
	}

	public Long getAnswerId4() {
		return answerId4;
	}

	public void setAnswerId4(Long answerId4) {
		this.answerId4 = answerId4;
	}

	public Answer getAnswer4() {
		return answer4;
	}

	public void setAnswer4(Answer answer4) {
		this.answer4 = answer4;
	}

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
	

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}
}
