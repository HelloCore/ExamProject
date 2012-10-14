package th.ac.kbu.cs.ExamProject.Domain;

public class ContentListPrototype {
	private Long courseId;
	private Long folderId;

	private Long from;
	
	public Long getFrom() {
		return from;
	}
	public void setFrom(Long from) {
		this.from = from;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	
}
