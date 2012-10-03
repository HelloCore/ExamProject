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
@Table(name = "ASSIGNMENT_TASK")
public class AssignmentTask implements Serializable{
	
	private static final long serialVersionUID = 2454727420914076311L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ASSIGNMENT_TASK_ID")
	private Long assignmentTaskId;

	@Column(name = "ASSIGNMENT_TASK_NAME")
	private String assignmentTaskName;
	
	@Column(name = "ASSIGNMENT_DESC")
	private String assignmentTaskDesc;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "NUM_OF_FILE")
	private Integer numOfFile;
	
	@Column(name = "LIMIT_FILE_SIZE_KB")
	private Long limitFileSizeKb;
	
	@Column(name = "COURSE_ID")
	private Long courseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", insertable = false, updatable = false)
	private Course course;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "CREATE_BY")
	private String createBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_BY", insertable = false, updatable = false)
	private User user;
	
	@Column(name = "MAX_SCORE")
	private Integer maxScore;
	
	@Column(name = "FLAG")
	private Boolean flag;

	public Long getAssignmentTaskId() {
		return assignmentTaskId;
	}

	public void setAssignmentTaskId(Long assignmentTaskId) {
		this.assignmentTaskId = assignmentTaskId;
	}

	public String getAssignmentTaskName() {
		return assignmentTaskName;
	}

	public void setAssignmentTaskName(String assignmentTaskName) {
		this.assignmentTaskName = assignmentTaskName;
	}

	public String getAssignmentTaskDesc() {
		return assignmentTaskDesc;
	}

	public void setAssignmentTaskDesc(String assignmentTaskDesc) {
		this.assignmentTaskDesc = assignmentTaskDesc;
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

	public Integer getNumOfFile() {
		return numOfFile;
	}

	public void setNumOfFile(Integer numOfFile) {
		this.numOfFile = numOfFile;
	}

	public Long getLimitFileSizeKb() {
		return limitFileSizeKb;
	}

	public void setLimitFileSizeKb(Long limitFileSizeKb) {
		this.limitFileSizeKb = limitFileSizeKb;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	
}
