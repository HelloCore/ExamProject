package th.ac.kbu.cs.ExamProject.Domain;

public class QuestionGroupPrototype {
	private Long questionGroupId;
	private String questionGroupName;
	private Long courseId;
	
	private String questionGroupNameSearch;
	private String courseCodeSearch;
	public String getQuestionGroupNameSearch() {
		return questionGroupNameSearch;
	}
	public void setQuestionGroupNameSearch(String questionGroupNameSearch) {
		this.questionGroupNameSearch = questionGroupNameSearch;
	}
	public String getCourseCodeSearch() {
		return courseCodeSearch;
	}
	public void setCourseCodeSearch(String courseCodeSearch) {
		this.courseCodeSearch = courseCodeSearch;
	}
	public Long getQuestionGroupId() {
		return questionGroupId;
	}
	public void setQuestionGroupId(Long questionGroupId) {
		this.questionGroupId = questionGroupId;
	}
	public String getQuestionGroupName() {
		return questionGroupName;
	}
	public void setQuestionGroupName(String questionGroupName) {
		this.questionGroupName = questionGroupName;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

}
