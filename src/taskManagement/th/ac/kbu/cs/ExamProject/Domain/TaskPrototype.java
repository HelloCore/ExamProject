package th.ac.kbu.cs.ExamProject.Domain;


public class TaskPrototype {
	
	private Long courseId;
	private Long sectionId;
	private Long taskId;
	private Long workId;
	private Long file;
	private String oldSectionStr;
	private String sectionIdStr;
	private String taskName;
	private String taskDesc;
	private String startDate;
	private String endDate;
	private Integer numOfFile;
	private Long limitFileSizeKb;
	private Float maxScore;
	private Float score;
	private Boolean isComplete;
	
	public Long getCourseId() {
		return courseId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	public String getSectionIdStr() {
		return sectionIdStr;
	}
	public void setSectionIdStr(String sectionIdStr) {
		this.sectionIdStr = sectionIdStr;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
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
	public String getOldSectionStr() {
		return oldSectionStr;
	}
	public void setOldSectionStr(String oldSectionStr) {
		this.oldSectionStr = oldSectionStr;
	}
	public Integer getNumOfFile() {
		return numOfFile;
	}
	public void setNumOfFile(Integer numOfFile) {
		this.numOfFile = numOfFile;
	}
	public Long getLimitFileSizeKb() {
		return limitFileSizeKb;
	}
	public void setLimitFileSizeKb(Long limitFileSizeKb) {
		this.limitFileSizeKb = limitFileSizeKb;
	}
	public Float getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}
	public Long getWorkId() {
		return workId;
	}
	public void setWorkId(Long workId) {
		this.workId = workId;
	}
	public Long getFile() {
		return file;
	}
	public void setFile(Long file) {
		this.file = file;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Boolean getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}
	
}
