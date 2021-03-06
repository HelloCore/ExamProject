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
@Table(name = "ASSIGNMENT_WORK")
public class AssignmentWork implements Serializable{

	private static final long serialVersionUID = 4111094186821128328L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ASSIGNMENT_WORK_ID",updatable=false)
	private Long assignmentWorkId;

	@Column(name = "ASSIGNMENT_TASK_ID",updatable=false)
	private Long assignmentTaskId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSIGNMENT_TASK_ID", insertable = false, updatable = false)
	private AssignmentTask assignmentTask;
	
	@Column(name = "SCORE")
	private Float score;
	
	@Column(name = "SEND_DATE",updatable=false)
	private Date sendDate;
	
	@Column(name = "SEND_BY",updatable=false)
	private String sendBy;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "EVALUATE_DATE")
	private Date evaluateDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SEND_BY", insertable = false, updatable = false)
	private User user;

	public Long getAssignmentWorkId() {
		return assignmentWorkId;
	}

	public void setAssignmentWorkId(Long assignmentWorkId) {
		this.assignmentWorkId = assignmentWorkId;
	}

	public Long getAssignmentTaskId() {
		return assignmentTaskId;
	}

	public void setAssignmentTaskId(Long assignmentTaskId) {
		this.assignmentTaskId = assignmentTaskId;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendBy() {
		return sendBy;
	}

	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AssignmentTask getAssignmentTask() {
		return assignmentTask;
	}

	public void setAssignmentTask(AssignmentTask assignmentTask) {
		this.assignmentTask = assignmentTask;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getEvaluateDate() {
		return evaluateDate;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}
	
	
	
}
