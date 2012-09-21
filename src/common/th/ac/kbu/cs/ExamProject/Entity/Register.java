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
@Table(name = "REGISTER")
@org.hibernate.annotations.Entity(dynamicUpdate=true)

public class Register implements Serializable{
	
	private static final long serialVersionUID = -192094084155282724L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "REGISTER_ID")
	private Long registerId;
	
	@Column(name = "SECTION_ID")
	private Long sectionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECTION_ID", insertable = false, updatable = false)
	private Section section;
	
	@Column(name = "USERNAME")
	private String username;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME", insertable = false, updatable = false)
	private User user;
	
	@Column(name = "REQUEST_DATE")
	private Date requestDate;
	
	@Column(name = "PROCESS_DATE")
	private Date processDate;
	
	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "VERIFY_BY")
	private String verifyBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VERIFY_BY", insertable = false, updatable = false)
	private User teacher;
	
	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
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

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVerifyBy() {
		return verifyBy;
	}

	public void setVerifyBy(String verifyBy) {
		this.verifyBy = verifyBy;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

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
	
	
	
}
