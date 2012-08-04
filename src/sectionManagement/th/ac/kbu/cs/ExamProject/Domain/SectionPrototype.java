package th.ac.kbu.cs.ExamProject.Domain;

public class SectionPrototype {
	private String courseCode;
	private Long courseId;

	private Long sectionId;
	private String sectionName;
	private Integer sectionYear;
	private Integer sectionSemester;
	
	private String sectionNameSearch;
	private String sectionYearSearch;
	private String sectionSemesterSearch;
	private String courseCodeSearch;
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Integer getSectionYear() {
		return sectionYear;
	}
	public void setSectionYear(Integer sectionYear) {
		this.sectionYear = sectionYear;
	}
	public Integer getSectionSemester() {
		return sectionSemester;
	}
	public void setSectionSemester(Integer sectionSemester) {
		this.sectionSemester = sectionSemester;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	

	public String getSectionNameSearch() {
		return sectionNameSearch;
	}
	public void setSectionNameSearch(String sectionNameSearch) {
		this.sectionNameSearch = sectionNameSearch;
	}
	public String getSectionYearSearch() {
		return sectionYearSearch;
	}
	public void setSectionYearSearch(String sectionYearSearch) {
		this.sectionYearSearch = sectionYearSearch;
	}
	public String getSectionSemesterSearch() {
		return sectionSemesterSearch;
	}
	public void setSectionSemesterSearch(String sectionSemesterSearch) {
		this.sectionSemesterSearch = sectionSemesterSearch;
	}
	public String getCourseCodeSearch() {
		return courseCodeSearch;
	}
	public void setCourseCodeSearch(String courseCodeSearch) {
		this.courseCodeSearch = courseCodeSearch;
	}
}
