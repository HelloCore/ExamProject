package th.ac.kbu.cs.ExamProject.Bean;

public class ExamData {
	private Long examId;
	private Float maxScore;
	private String examHeader;
	public Long getExamId() {
		return examId;
	}
	public void setExamId(Long examId) {
		this.examId = examId;
	}
	public Float getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}
	public String getExamHeader() {
		return examHeader;
	}
	public void setExamHeader(String examHeader) {
		this.examHeader = examHeader;
	}
}
