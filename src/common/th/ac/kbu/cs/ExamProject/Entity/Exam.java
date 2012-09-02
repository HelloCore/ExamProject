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
@Table(name = "EXAM")
public class Exam implements Serializable{

	private static final long serialVersionUID = -4712172563019856856L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "EXAM_ID")
	private Long examId;
	
	@Column(name = "EXAM_HEADER",length=200)
	private String examHeader;

	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "EXAM_LIMIT")
	private Integer examLimit;
	
	@Column(name = "MIN_QUESTION")
	private Integer minQuestion;
	
	@Column(name = "MAX_QUESTION")
	private Integer maxQuestion;
	
	@Column(name = "COURSE_ID")
	private Long courseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", insertable = false, updatable = false)
	private Course course;
	
	@Column(name = "EXAM_SEQUENCE")
	private Boolean examSequence;
	
	@Column(name = "FLAG")
	private Boolean flag;

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public String getExamHeader() {
		return examHeader;
	}

	public void setExamHeader(String examHeader) {
		this.examHeader = examHeader;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getMinQuestion() {
		return minQuestion;
	}

	public void setMinQuestion(Integer minQuestion) {
		this.minQuestion = minQuestion;
	}

	public Integer getMaxQuestion() {
		return maxQuestion;
	}

	public void setMaxQuestion(Integer maxQuestion) {
		this.maxQuestion = maxQuestion;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Boolean getExamSequence() {
		return examSequence;
	}

	public void setExamSequence(Boolean examSequence) {
		this.examSequence = examSequence;
	}

	public Integer getExamLimit() {
		return examLimit;
	}

	public void setExamLimit(Integer examLimit) {
		this.examLimit = examLimit;
	}
	
}
