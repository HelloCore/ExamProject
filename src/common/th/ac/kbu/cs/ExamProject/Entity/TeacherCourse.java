package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;

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
@Table(name = "TEACHER_COURSE")
public class TeacherCourse implements Serializable{

	private static final long serialVersionUID = -7148553897806518395L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "TEACHER_COURSE_ID")
	private Long teacherCourseId;
	
	@Column(name = "COURSE_ID")
	private Long courseId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", insertable = false, updatable = false)
	private Course course;
	
	@Column(name = "USERNAME",length = 40)
	private String username;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME", insertable = false, updatable = false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getTeacherCourseId() {
		return teacherCourseId;
	}

	public void setTeacherCourseId(Long teacherCourseId) {
		this.teacherCourseId = teacherCourseId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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
}
