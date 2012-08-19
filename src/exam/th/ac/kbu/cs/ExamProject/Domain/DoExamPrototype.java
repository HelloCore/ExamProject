package th.ac.kbu.cs.ExamProject.Domain;

public class DoExamPrototype{
	private Integer numOfQuestion;
	private Long examId;
	private Integer examCount;
	public Integer getExamCount() {
		return examCount;
	}
	public void setExamCount(Integer examCount) {
		this.examCount = examCount;
	}
	public Integer getNumOfQuestion() {
		return numOfQuestion;
	}
	public void setNumOfQuestion(Integer numOfQuestion) {
		this.numOfQuestion = numOfQuestion;
	}
	public Long getExamId() {
		return examId;
	}
	public void setExamId(Long examId) {
		this.examId = examId;
	}
}
