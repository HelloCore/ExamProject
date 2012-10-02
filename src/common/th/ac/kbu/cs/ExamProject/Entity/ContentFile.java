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
@Table(name = "CONTENT_FILE")
public class ContentFile implements Serializable{

	private static final long serialVersionUID = -2356043291485298384L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "CONTENT_FILE_ID")
	private Long contentFileId;

	@Column(name = "CONTENT_FILE_NAME")
	private String contentFileName;

	@Column(name = "CONTENT_FILE_PATH")
	private String contentFilePath;

	@Column(name = "CONTENT_FILE_DESC")
	private String contentFileDesc;
	
	@Column(name = "CONTENT_FILE_SIZE")
	private Long contentFileSize;

	@Column(name = "CONTENT_FILE_TYPE")
	private Integer contentFileType;
	
	@Column(name = "CONTENT_PATH_ID")
	private Long contentPathId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENT_PATH_ID", insertable = false, updatable = false)
	private ContentPath contentPath;

	@Column(name = "CREATE_DATETIME")
	private Date createDate;
	
	@Column(name = "CREATE_BY")
	private String createBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_BY", insertable = false, updatable = false)
	private User user;

	public Long getContentFileId() {
		return contentFileId;
	}

	public void setContentFileId(Long contentFileId) {
		this.contentFileId = contentFileId;
	}

	public String getContentFileName() {
		return contentFileName;
	}

	public void setContentFileName(String contentFileName) {
		this.contentFileName = contentFileName;
	}

	public String getContentFilePath() {
		return contentFilePath;
	}

	public void setContentFilePath(String contentFilePath) {
		this.contentFilePath = contentFilePath;
	}

	public Long getContentFileSize() {
		return contentFileSize;
	}

	public void setContentFileSize(Long contentFileSize) {
		this.contentFileSize = contentFileSize;
	}

	public Long getContentPathId() {
		return contentPathId;
	}

	public void setContentPathId(Long contentPathId) {
		this.contentPathId = contentPathId;
	}

	public ContentPath getContentPath() {
		return contentPath;
	}

	public void setContentPath(ContentPath contentPath) {
		this.contentPath = contentPath;
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

	public String getContentFileDesc() {
		return contentFileDesc;
	}

	public void setContentFileDesc(String contentFileDesc) {
		this.contentFileDesc = contentFileDesc;
	}

	public Integer getContentFileType() {
		return contentFileType;
	}

	public void setContentFileType(Integer contentFileType) {
		this.contentFileType = contentFileType;
	}
	
	
}
