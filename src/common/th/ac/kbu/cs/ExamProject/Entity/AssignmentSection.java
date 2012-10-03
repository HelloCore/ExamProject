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
@Table(name = "ASSIGNMENT_SECTION")
public class AssignmentSection implements Serializable{

	private static final long serialVersionUID = 5274169978821274903L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ASSIGNMENT_SECTION_ID")
	private Long assignmentSectionId;

	@Column(name = "ASSIGNMENT_TASK_ID")
	private Long assignmentTaskId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSIGNMENT_TASK_ID", insertable = false, updatable = false)
	private AssignmentTask assignmentTask;
	
	@Column(name = "SECTION_ID")
	private Long sectionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECTION_ID", insertable = false, updatable = false)
	private Section section;

	public Long getAssignmentSectionId() {
		return assignmentSectionId;
	}

	public void setAssignmentSectionId(Long assignmentSectionId) {
		this.assignmentSectionId = assignmentSectionId;
	}

	public Long getAssignmentTaskId() {
		return assignmentTaskId;
	}

	public void setAssignmentTaskId(Long assignmentTaskId) {
		this.assignmentTaskId = assignmentTaskId;
	}

	public AssignmentTask getAssignmentTask() {
		return assignmentTask;
	}

	public void setAssignmentTask(AssignmentTask assignmentTask) {
		this.assignmentTask = assignmentTask;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
	
	
	
}
