package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "EXAM_RESULT")
public class ExamResult implements Serializable{

	private static final long serialVersionUID = -6231334594396043773L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "EXAM_RESULT_ID")
	private Long examResultId;
	
	@Column(name = "EXAM_ID")
	private Long examId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXAM_ID", insertable = false, updatable = false)
	private Exam exam;
	
	@Column(name = "USERNAME")
	private String username;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME", insertable = false, updatable = false)
	private User user;
	
	@Column(name = "NUM_OF_QUESTION")
	private Integer numOfQuestion;
	
	@Column(name = "EXAM_COUNT")
	private Integer examCount;
	
	@Column(name = "EXAM_START_DATE")
	private Date examStartDate;
	
	@Column(name = "EXAM_LIMIT_TIME")
	private Integer examLimitTime;
	
	@Column(name = "EXAM_COMPLETE_DATE")
	private Date examCompleteDate;
	
	@Column(name = "EXAM_COMPLETED")
	private Boolean examCompleted;

	@Column(name= "EXAM_USED_TIME")
	private Integer examUsedTime;
	
	public Long getExamResultId() {
		return examResultId;
	}

	public void setExamResultId(Long examResultId) {
		this.examResultId = examResultId;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getNumOfQuestion() {
		return numOfQuestion;
	}

	public void setNumOfQuestion(Integer numOfQuestion) {
		this.numOfQuestion = numOfQuestion;
	}

	public Integer getExamCount() {
		return examCount;
	}

	public void setExamCount(Integer examCount) {
		this.examCount = examCount;
	}

	public Date getExamStartDate() {
		return examStartDate;
	}

	public void setExamStartDate(Date examStartDate) {
		this.examStartDate = examStartDate;
	}

	public Integer getExamLimitTime() {
		return examLimitTime;
	}

	public void setExamLimitTime(Integer examLimitTime) {
		this.examLimitTime = examLimitTime;
	}

	public Date getExamCompleteDate() {
		return examCompleteDate;
	}

	public void setExamCompleteDate(Date examCompleteDate) {
		this.examCompleteDate = examCompleteDate;
	}

	public Boolean getExamCompleted() {
		return examCompleted;
	}

	public void setExamCompleted(Boolean examCompleted) {
		this.examCompleted = examCompleted;
	}

	public Integer getExamUsedTime() {
		return examUsedTime;
	}

	public void setExamUsedTime(Integer examUsedTime) {
		this.examUsedTime = examUsedTime;
	}

	
}
