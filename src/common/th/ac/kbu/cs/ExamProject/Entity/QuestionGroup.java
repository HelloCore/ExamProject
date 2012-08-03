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
@Table(name = "QUESTION_GROUP")
public class QuestionGroup implements Serializable{

	private static final long serialVersionUID = 1218136712065678949L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "QUESTION_GROUP_ID")
	private Long questionGroupId;
	
	@Column(name = "QUESTION_GROUP_NAME",length=100)
	private String questionGroupName;
	
	@Column(name = "COURSE_ID")
	private Long courseId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", insertable = false, updatable = false)
	private Course course;

	public Long getQuestionGroupId() {
		return questionGroupId;
	}

	public void setQuestionGroupId(Long questionGroupId) {
		this.questionGroupId = questionGroupId;
	}

	public String getQuestionGroupName() {
		return questionGroupName;
	}

	public void setQuestionGroupName(String questionGroupName) {
		this.questionGroupName = questionGroupName;
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
	
}
