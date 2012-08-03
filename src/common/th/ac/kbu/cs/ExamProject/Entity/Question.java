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
@Table(name = "QUESTION")
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class Question implements Serializable{

	private static final long serialVersionUID = 6046536182420617545L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "QUESTION_ID")
	private Long questionId;
	
	@Column(name = "QUESTION_TEXT")
	private String questionText;
	
	@Column(name = "QUESTION_GROUP_ID")
	private Long questionGroupId;
	
	@Column(name = "FLAG")
	private Boolean flag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_GROUP_ID", insertable = false, updatable = false)
	private QuestionGroup questionGroup;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}


	public Long getQuestionGroupId() {
		return questionGroupId;
	}

	public void setQuestionGroupId(Long questionGroupId) {
		this.questionGroupId = questionGroupId;
	}

	public QuestionGroup getQuestionGroup() {
		return questionGroup;
	}

	public void setQuestionGroup(QuestionGroup questionGroup) {
		this.questionGroup = questionGroup;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	
}
