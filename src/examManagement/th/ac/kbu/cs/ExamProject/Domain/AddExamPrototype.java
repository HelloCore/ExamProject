package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;

public class AddExamPrototype {
	private String examHeader;
	private Long courseId;
	private String startDate;
	private String endDate;
	private Integer minQuestion;
	private Integer maxQuestion;
	private Integer examCount;
	private String sectionData;
	private String questionGroupData;
	private Boolean examSequence;
	public String getExamHeader() {
		return examHeader;
	}
	public void setExamHeader(String examHeader) {
		this.examHeader = examHeader;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getQuestionGroupData() {
		return questionGroupData;
	}
	public void setQuestionGroupData(String questionGroupData) {
		this.questionGroupData = questionGroupData;
	}
	public Boolean getExamSequence() {
		return examSequence;
	}
	public void setExamSequence(Boolean examSequence) {
		this.examSequence = examSequence;
	}
	public Integer getMinQuestion() {
		return minQuestion;
	}
	public void setMinQuestion(Integer minQuestion) {
		this.minQuestion = minQuestion;
	}
	public Integer getMaxQuestion() {
		return maxQuestion;
	}
	public void setMaxQuestion(Integer maxQuestion) {
		this.maxQuestion = maxQuestion;
	}
	public Integer getExamCount() {
		return examCount;
	}
	public void setExamCount(Integer examCount) {
		this.examCount = examCount;
	}
	public String getSectionData() {
		return sectionData;
	}
	public void setSectionData(String sectionData) {
		this.sectionData = sectionData;
	}

}
