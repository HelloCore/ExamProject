package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_SECTION")
public class MasterSection implements Serializable{
	
	private static final long serialVersionUID = -7578397022595396514L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "MASTER_SECTION_ID")
	private Long masterSectionId;
	
	public Long getMasterSectionId() {
		return masterSectionId;
	}

	public void setMasterSectionId(Long masterSectionId) {
		this.masterSectionId = masterSectionId;
	}

	@Column(name = "SECTION_YEAR",length=4)
	private Integer sectionYear;

	@Column(name = "SECTION_SEMESTER",length=1)
	private Integer sectionSemester;

	@Column(name = "FLAG")
	private Boolean flag;

	@Column(name = "STATUS")
	private Integer status;
	
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Integer getSectionYear() {
		return sectionYear;
	}

	public void setSectionYear(Integer sectionYear) {
		this.sectionYear = sectionYear;
	}

	public Integer getSectionSemester() {
		return sectionSemester;
	}

	public void setSectionSemester(Integer sectionSemester) {
		this.sectionSemester = sectionSemester;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
