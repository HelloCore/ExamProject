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
@Table(name = "SECTION")
public class Section implements Serializable{
	
	private static final long serialVersionUID = 6128342457392230951L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "SECTION_ID")
	private Long sectionId;

	@Column(name = "SECTION_NAME",length=10)
	private String sectionName;

	@Column(name = "COURSE_ID")
	private Long courseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", insertable = false, updatable = false)
	private Course course;
	
	@Column(name = "FLAG")
	private Boolean flag;


	@Column(name = "MASTER_SECTION_ID")
	private Long masterSectionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MASTER_SECTION_ID", insertable = false, updatable = false)
	private MasterSection masterSection;
	
	public Long getMasterSectionId() {
		return masterSectionId;
	}

	public void setMasterSectionId(Long masterSectionId) {
		this.masterSectionId = masterSectionId;
	}

	public MasterSection getMasterSection() {
		return masterSection;
	}

	public void setMasterSection(MasterSection masterSection) {
		this.masterSection = masterSection;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
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

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	
}
