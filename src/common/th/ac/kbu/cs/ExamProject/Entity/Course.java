package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "COURSE")
public class Course implements Serializable{
	
	private static final long serialVersionUID = 6507058981489565024L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "COURSE_ID")
	private Long courseId;

	@Column(name = "COURSE_CODE",length=10)
	private String courseCode;

	@Column(name = "COURSE_NAME",length=50)
	private String courseName;

	@Column(name = "FLAG")
	private Boolean flag;
	
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="COURSE_ID")
	private List<Section> section;
	
	public List<Section> getSection() {
		return section;
	}

	public void setSection(List<Section> section) {
		this.section = section;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

}
