package th.ac.kbu.cs.ExamProject.Domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class ExamObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1734993893274944559L;
	private String courseCode;
	private Long examId;
	private String examHeader;
	private Date endDate;
	private Integer examLimit;
	private Integer examCount;
	private Integer minQuestion;
	private Integer maxQuestion;
	private Integer canDoExam;
	
	public ExamObject(Object[] obj) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.courseCode = BeanUtils.toString(obj[0]);
		this.examId = BeanUtils.toLong(obj[1]);
		this.examHeader = BeanUtils.toString(obj[2]);
		if(BeanUtils.isNotEmpty(obj[3])){
			this.endDate = sdf.parse(obj[3].toString());
		}
		this.examLimit = BeanUtils.toInteger(obj[4]);
		this.examCount = BeanUtils.toInteger(obj[5]);
		this.minQuestion = BeanUtils.toInteger(obj[6]);
		this.maxQuestion =  BeanUtils.toInteger(obj[7]);
		this.canDoExam = this.examLimit - this.examCount;
	
	}
	public Integer getCanDoExam() {
		return canDoExam;
	}
	public void setCanDoExam(Integer canDoExam) {
		this.canDoExam = canDoExam;
	}
	public Integer getExamCount() {
		return examCount;
	}
	public void setExamCount(Integer examCount) {
		this.examCount = examCount;
	}
	public Long getExamId() {
		return examId;
	}
	public void setExamId(Long examId) {
		this.examId = examId;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getExamHeader() {
		return examHeader;
	}
	public void setExamHeader(String examHeader) {
		this.examHeader = examHeader;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getExamLimit() {
		return examLimit;
	}
	public void setExamLimit(Integer examLimit) {
		this.examLimit = examLimit;
	}
	public Integer getMinQuestion() {
		return minQuestion;
	}
	public void setMinQuestion(Integer minQuestion) {
		this.minQuestion = minQuestion;
	}
	public Integer getMaxQuestion() {
		return maxQuestion;
	}
	public void setMaxQuestion(Integer maxQuestion) {
		this.maxQuestion = maxQuestion;
	}
	@Override
	public String toString() {
		return "ExamObject [courseCode=" + courseCode + ", examId=" + examId
				+ ", examHeader=" + examHeader + ", endDate=" + endDate
				+ ", examLimit=" + examLimit + ", examCount=" + examCount
				+ ", minQuestion=" + minQuestion + ", maxQuestion="
				+ maxQuestion + "]";
	}
	
}
