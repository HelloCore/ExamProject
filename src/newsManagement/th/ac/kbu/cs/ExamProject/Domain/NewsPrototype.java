package th.ac.kbu.cs.ExamProject.Domain;

public class NewsPrototype {
	private Long id;
	private Long newsId;
	private String newsHeader;
	private String newsContent;
	private Long courseId;
	private String courseCodeSearch;
	private String newsHeaderSearch;
	private String newsContentSearch;
	
	
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Long getNewsId() {
		return newsId;
	}
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	public String getNewsHeader() {
		return newsHeader;
	}
	public void setNewsHeader(String newsHeader) {
		this.newsHeader = newsHeader;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	public String getCourseCodeSearch() {
		return courseCodeSearch;
	}
	public void setCourseCodeSearch(String courseCodeSearch) {
		this.courseCodeSearch = courseCodeSearch;
	}
	public String getNewsHeaderSearch() {
		return newsHeaderSearch;
	}
	public void setNewsHeaderSearch(String newsHeaderSearch) {
		this.newsHeaderSearch = newsHeaderSearch;
	}
	public String getNewsContentSearch() {
		return newsContentSearch;
	}
	public void setNewsContentSearch(String newsContentSearch) {
		this.newsContentSearch = newsContentSearch;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
