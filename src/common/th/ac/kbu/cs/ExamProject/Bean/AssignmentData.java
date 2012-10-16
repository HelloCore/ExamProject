package th.ac.kbu.cs.ExamProject.Bean;

public class AssignmentData {
	private Long assignmentTaskId;
	private String assignmentTaskName;
	private Double maxScore;
	
	public Long getAssignmentTaskId() {
		return assignmentTaskId;
	}
	public void setAssignmentTaskId(Long assignmentTaskId) {
		this.assignmentTaskId = assignmentTaskId;
	}
	public String getAssignmentTaskName() {
		return assignmentTaskName;
	}
	public void setAssignmentTaskName(String assignmentTaskName) {
		this.assignmentTaskName = assignmentTaskName;
	}
	public Double getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}
}
