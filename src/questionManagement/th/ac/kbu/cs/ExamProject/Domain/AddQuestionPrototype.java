package th.ac.kbu.cs.ExamProject.Domain;

public class AddQuestionPrototype {
	private Long courseId;
	private Long questionGroupId;
	private String questionText;
	private String answerStr;
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Long getQuestionGroupId() {
		return questionGroupId;
	}
	public void setQuestionGroupId(Long questionGroupId) {
		this.questionGroupId = questionGroupId;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getAnswerStr() {
		return answerStr;
	}
	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
	}
}
