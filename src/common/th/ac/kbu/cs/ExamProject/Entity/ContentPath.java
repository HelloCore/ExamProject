package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CONTENT_PATH")
public class ContentPath implements Serializable{

	private static final long serialVersionUID = -6341575207000837890L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "CONTENT_PATH_ID")
	private Long contentPathId;

	@Column(name = "CONTENT_PATH_NAME")
	private String contentPathName;

	@Column(name = "CONTENT_PATH_DESC")
	private String contentPathDesc;
	
	@Column(name = "CONTENT_PATH_LOCATION")
	private String contentPathLocation;
	
	@Column(name = "PARENT_PATH_ID")
	private Long parentPathId;

	@OneToMany(cascade={CascadeType.REMOVE})
    @JoinColumn(name="PARENT_PATH_ID")
    private List<ContentPath> contentPaths;

	@OneToMany(cascade={CascadeType.REMOVE})
    @JoinColumn(name="CONTENT_PATH_ID")
    private List<ContentFile> contentFiles;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_PATH_ID", insertable = false, updatable = false)
	private ContentPath parentPath;
	
	@Column(name = "COURSE_ID")
	private Long courseId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", insertable = false, updatable = false)
	private Course course;

	public Long getContentPathId() {
		return contentPathId;
	}

	public void setContentPathId(Long contentPathId) {
		this.contentPathId = contentPathId;
	}

	public String getContentPathName() {
		return contentPathName;
	}

	public void setContentPathName(String contentPathName) {
		this.contentPathName = contentPathName;
	}
	
	public Long getParentPathId() {
		return parentPathId;
	}

	public void setParentPathId(Long parentPathId) {
		this.parentPathId = parentPathId;
	}

	public ContentPath getParentPath() {
		return parentPath;
	}

	public void setParentPath(ContentPath parentPath) {
		this.parentPath = parentPath;
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

	public String getContentPathDesc() {
		return contentPathDesc;
	}

	public void setContentPathDesc(String contentPathDesc) {
		this.contentPathDesc = contentPathDesc;
	}

	public String getContentPathLocation() {
		return contentPathLocation;
	}

	public void setContentPathLocation(String contentPathLocation) {
		this.contentPathLocation = contentPathLocation;
	}

	public List<ContentPath> getContentPaths() {
		return contentPaths;
	}

	public void setContentPaths(List<ContentPath> contentPaths) {
		this.contentPaths = contentPaths;
	}

	public List<ContentFile> getContentFiles() {
		return contentFiles;
	}

	public void setContentFiles(List<ContentFile> contentFiles) {
		this.contentFiles = contentFiles;
	}
	
	
}
