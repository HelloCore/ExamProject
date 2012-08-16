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
@Table(name = "EXAM_QUESTION_GROUP")
public class ExamQuestionGroup implements Serializable{

	private static final long serialVersionUID = -8903387753440853268L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "EXAM_QUESTION_GROUP_ID")
	private Long examQuestionGroupId;
	
	@Column(name = "EXAM_ID")
	private Long examId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXAM_ID", insertable = false, updatable = false)
	private Exam exam;
	
	@Column(name = "QUESTION_GROUP_ID")
	private Long questionGroupId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_GROUP_ID", insertable = false, updatable = false)
	private QuestionGroup questionGroup;
	
	@Column(name = "QUESTION_PERCENT")
	private Integer questionPercent;

	@Column(name = "ORDINAL")
	private Integer ordinal;
	
	
	@Column(name = "SECOND_PER_QUESTION")
	private Integer secondPerQuestion;
	
	public Long getExamQuestionGroupId() {
		return examQuestionGroupId;
	}

	public void setExamQuestionGroupId(Long examQuestionGroupId) {
		this.examQuestionGroupId = examQuestionGroupId;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Long getQuestionGroupId() {
		return questionGroupId;
	}

	public void setQuestionGroupId(Long questionGroupId) {
		this.questionGroupId = questionGroupId;
	}

	public QuestionGroup getQuestionGroup() {
		return questionGroup;
	}

	public void setQuestionGroup(QuestionGroup questionGroup) {
		this.questionGroup = questionGroup;
	}

	public Integer getQuestionPercent() {
		return questionPercent;
	}

	public void setQuestionPercent(Integer questionPercent) {
		this.questionPercent = questionPercent;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public Integer getSecondPerQuestion() {
		return secondPerQuestion;
	}

	public void setSecondPerQuestion(Integer secondPerQuestion) {
		this.secondPerQuestion = secondPerQuestion;
	}
	
	
	
}
