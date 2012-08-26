package th.ac.kbu.cs.ExamProject.Domain;

public class ExamPrototype {
	private Long examId;
	private Long courseId;
	private String examHeader;
	private String examQuestionGroupSaveStr;
	private String examQuestionGroupDeleteStr;
	
	private String saveSectionDataStr;
	private String deletedSectionDataStr;
	
	public String getExamQuestionGroupSaveStr() {
		return examQuestionGroupSaveStr;
	}

	public void setExamQuestionGroupSaveStr(String examQuestionGroupSaveStr) {
		this.examQuestionGroupSaveStr = examQuestionGroupSaveStr;
	}

	public String getExamQuestionGroupDeleteStr() {
		return examQuestionGroupDeleteStr;
	}

	public void setExamQuestionGroupDeleteStr(String examQuestionGroupDeleteStr) {
		this.examQuestionGroupDeleteStr = examQuestionGroupDeleteStr;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getExamHeader() {
		return examHeader;
	}

	public void setExamHeader(String examHeader) {
		this.examHeader = examHeader;
	}

	public String getSaveSectionDataStr() {
		return saveSectionDataStr;
	}

	public void setSaveSectionDataStr(String saveSectionDataStr) {
		this.saveSectionDataStr = saveSectionDataStr;
	}

	public String getDeletedSectionDataStr() {
		return deletedSectionDataStr;
	}

	public void setDeletedSectionDataStr(String deletedSectionDataStr) {
		this.deletedSectionDataStr = deletedSectionDataStr;
	}

}
