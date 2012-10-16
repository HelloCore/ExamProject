package th.ac.kbu.cs.ExamProject.Bean;

public class ExamData {
	private Long examId;
	private Double examScore;
	private String examHeader;
	public Long getExamId() {
		return examId;
	}
	public void setExamId(Long examId) {
		this.examId = examId;
	}
	public Double getExamScore() {
		return examScore;
	}
	public void setExamScore(Double examScore) {
		this.examScore = examScore;
	}
	public String getExamHeader() {
		return examHeader;
	}
	public void setExamHeader(String examHeader) {
		this.examHeader = examHeader;
	}
}
