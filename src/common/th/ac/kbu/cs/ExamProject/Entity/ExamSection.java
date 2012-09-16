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
@Table(name = "EXAM_SECTION")
public class ExamSection implements Serializable {
	
	private static final long serialVersionUID = -7801755669588257440L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "EXAM_SECTION_ID")
	private Long examSectionId;
	
	@Column(name = "EXAM_ID")
	private Long examId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXAM_ID", insertable = false, updatable = false)
	private Exam exam;
	
	@Column(name = "SECTION_ID")
	private Long sectionId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECTION_ID", insertable = false, updatable = false)
	private Section section;

	public Long getExamSectionId() {
		return examSectionId;
	}

	public void setExamSectionId(Long examSectionId) {
		this.examSectionId = examSectionId;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
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
