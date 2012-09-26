package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NEWS")
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class News implements Serializable{

	private static final long serialVersionUID = 2684898730572218605L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "NEWS_ID")
	private Long newsId;

	@Column(name = "NEWS_HEADER")
	private String newsHeader;

	@Column(name = "NEWS_CONTENT")
	private String newsContent;

	@Column(name = "COURSE_ID")
	private Long courseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", insertable = false, updatable = false)
	private Course course;
	
	@Column(name = "FLAG")
	private Boolean flag;

	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	@Column(name = "USERNAME")
	private String username;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME", insertable = false,updatable = false)
	private User user;
	
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

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
