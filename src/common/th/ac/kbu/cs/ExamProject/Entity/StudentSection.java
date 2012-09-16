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
@Table(name = "STUDENT_SECTION")
public class StudentSection implements Serializable{

	private static final long serialVersionUID = 4950681142786815586L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "STUDENT_SECTION_ID")
	private Long studentSectionId;
	
	@Column(name = "SECTION_ID")
	private Long sectionId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECTION_ID", insertable = false, updatable = false)
	private Section section;
	
	@Column(name = "USERNAME",length = 40)
	private String username;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME", insertable = false, updatable = false)
	private User user;
	
	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getStudentSectionId() {
		return studentSectionId;
	}

	public void setStudentSectionId(Long studentSectionId) {
		this.studentSectionId = studentSectionId;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
