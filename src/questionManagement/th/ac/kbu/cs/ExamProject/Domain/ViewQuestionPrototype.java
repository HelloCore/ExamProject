package th.ac.kbu.cs.ExamProject.Domain;

public class ViewQuestionPrototype {
	private Long questionId;
	private Long questionGroupId;
	private Long courseId;
	private String questionText;
	private Long answerId;
	private String answerText;
	private Integer answerScore;
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Long getQuestionGroupId() {
		return questionGroupId;
	}
	public void setQuestionGroupId(Long questionGroupId) {
		this.questionGroupId = questionGroupId;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public Integer getAnswerScore() {
		return answerScore;
	}
	public void setAnswerScore(Integer answerScore) {
		this.answerScore = answerScore;
	}
}
