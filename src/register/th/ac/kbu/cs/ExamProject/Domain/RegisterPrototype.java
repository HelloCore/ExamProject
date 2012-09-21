package th.ac.kbu.cs.ExamProject.Domain;

public class RegisterPrototype {
	private Long courseId;
	private Long sectionId;
	private Long registerId;
	private Long toSectionId;
	
	
	public Long getRegisterId() {
		return registerId;
	}
	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getToSectionId() {
		return toSectionId;
	}
	public void setToSectionId(Long toSectionId) {
		this.toSectionId = toSectionId;
	}
	
}
