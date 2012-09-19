package th.ac.kbu.cs.ExamProject.Domain;


public class RegisterManagementPrototype {
	private Long courseId;
	private Long sectionId;
	private String registerIdArray;
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
	public String getRegisterIdArray() {
		return registerIdArray;
	}
	public void setRegisterIdArray(String registerIdArray) {
		this.registerIdArray = registerIdArray;
	}
	
}
