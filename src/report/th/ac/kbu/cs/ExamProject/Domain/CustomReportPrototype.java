package th.ac.kbu.cs.ExamProject.Domain;

public class CustomReportPrototype {
	private Long courseId;
	private Long sectionId;
	private String examData;
	private Integer scoreChoice;
	private String assignmentData;
	
	public String getExamData() {
		return examData;
	}
	public void setExamData(String examData) {
		this.examData = examData;
	}
	public Integer getScoreChoice() {
		return scoreChoice;
	}
	public void setScoreChoice(Integer scoreChoice) {
		this.scoreChoice = scoreChoice;
	}
	public String getAssignmentData() {
		return assignmentData;
	}
	public void setAssignmentData(String assignmentData) {
		this.assignmentData = assignmentData;
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
}
