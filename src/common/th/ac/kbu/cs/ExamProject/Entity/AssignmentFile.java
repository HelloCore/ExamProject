package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "ASSIGNMENT_FILE")
public class AssignmentFile implements Serializable{

	private static final long serialVersionUID = 906600598136957918L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ASSIGNMENT_FILE_ID")
	private Long assignmentFileId;

	@Column(name = "ASSIGNMENT_WORK_ID")
	private Long assignmentWorkId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSIGNMENT_WORK_ID", insertable = false, updatable = false)
	private AssignmentWork assignmentWork;
	
	@Column(name = "CONTENT")
	@Lob
	private Blob content;

	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "CONTENT_TYPE")
	private String contentType;

	public Long getAssignmentFileId() {
		return assignmentFileId;
	}

	public void setAssignmentFileId(Long assignmentFileId) {
		this.assignmentFileId = assignmentFileId;
	}

	public Long getAssignmentWorkId() {
		return assignmentWorkId;
	}

	public void setAssignmentWorkId(Long assignmentWorkId) {
		this.assignmentWorkId = assignmentWorkId;
	}

	public AssignmentWork getAssignmentWork() {
		return assignmentWork;
	}

	public void setAssignmentWork(AssignmentWork assignmentWork) {
		this.assignmentWork = assignmentWork;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	

}
