package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class SelectExamDomain extends ExamPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	public List<Object[]> getExam(){
		
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
						.append("course.courseCode as courseCode")
						.append(",exam.examHeader as examHeader")
						.append(",exam.endDate as endDate")
						.append(",(SELECT COUNT(examResult.examResultId) ")
							.append(" FROM ExamResult examResult")
							.append(" WHERE examResult.examId = exam.examId ")
							.append(" AND examResult.username = ? ")
						.append(" ) as examCount ")
						.append(",exam.examLimit  as examLimit ")
						.append(",exam.minQuestion as minQuestion")
						.append(",exam.maxQuestion as maxQuestion")
						.append(",exam.examId as examId")
						.append(",exam.startDate as startDate")
						.append(",exam.isCalScore as isCalScore")
						.append(",exam.maxScore as maxScore")
						.append(" FROM ExamSection examSection ")
						.append(" JOIN examSection.exam exam ")
						.append(" JOIN exam.course course ")
						.append(" WHERE exam.flag=? ")
							.append(" AND ")
								.append(" ( ")
									.append(" exam.examLimit > ")
										.append(" (SELECT COUNT(examResult.examResultId) ")
											.append(" FROM ExamResult examResult")
											.append(" WHERE examResult.examId = exam.examId ")
											.append(" AND examResult.username = ? ")
										.append(" ) ")
								.append(" ) ")
							.append(" AND ( exam.endDate>? OR exam.endDate is null) ")
							.append(" AND ( exam.isCalScore = 1 AND examSection.sectionId in ")
									.append(" ( SELECT studentSection.sectionId ")
										.append(" FROM StudentSection studentSection ")
										.append(" WHERE studentSection.username = ? ")
									.append(" ) ) ")
						.append(" ORDER BY exam.isCalScore desc,exam.endDate asc");
		Object[] params = {
				SecurityUtils.getUsername(),
				true,
				SecurityUtils.getUsername(),
				new Date(),
				SecurityUtils.getUsername()
		};
		return basicFinderService.find(queryString.toString(), params);
	}
	
	public List<Object[]> getExamResult() {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
						.append(" course.courseCode as courseCode ")
						.append(" ,exam.examHeader as examHeader ")
						.append(" ,examResult.examCount as examCount ")
						.append(" ,examResult.examExpireDate as examExpireDate ")
						.append(" ,examResult.numOfQuestion as numOfQuestion ")
						.append(" ,( ")
								.append(" SELECT COUNT(examResultAnswer.examResultAnswerId) ")
								.append(" FROM ExamResultAnswer examResultAnswer")
								.append(" WHERE examResultAnswer.examResultId = examResult.examResultId ")
								.append(" AND examResultAnswer.answerId is not null ")
						.append(" ) as didQuestion ")
						.append(" ,examResult.examResultId as examResultId ")
						.append(" ,exam.isCalScore as isCalScore")
						.append(" ,exam.maxScore as maxScore")
					.append(" FROM ExamResult examResult")
						.append(" JOIN examResult.exam exam ")
						.append(" JOIN exam.course course ")
					.append(" WHERE ")
						.append(" examResult.examCompleted = ? ")
						.append(" AND examResult.username = ? ")
					.append(" ORDER BY ")
						.append(" exam.isCalScore desc,examResult.examExpireDate asc ");
		Object[] params = {
				false,
				SecurityUtils.getUsername()
		};
		return basicFinderService.find(queryString.toString(), params);
	}

	public List<Object[]> getSampleExam(){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
						.append("course.courseCode as courseCode")
						.append(",exam.examHeader as examHeader")
						.append(",exam.endDate as endDate")
						.append(",(SELECT COUNT(examResult.examResultId) ")
							.append(" FROM ExamResult examResult")
							.append(" WHERE examResult.examId = exam.examId ")
							.append(" AND examResult.username = ? ")
						.append(" ) as examCount ")
						.append(",exam.examLimit  as examLimit ")
						.append(",exam.minQuestion as minQuestion")
						.append(",exam.maxQuestion as maxQuestion")
						.append(",exam.examId as examId")
						.append(",exam.startDate as startDate")
						.append(",exam.isCalScore as isCalScore")
						.append(",exam.maxScore as maxScore")
						.append(" FROM Exam exam ")
						.append(" JOIN exam.course course ")
						.append(" WHERE exam.flag=? ")
							.append(" AND exam.courseId IN ( SELECT course.courseId FROM StudentSection studentSection JOIN studentSection.section section JOIN section.course course WHERE studentSection.username = ? )")
							.append(" AND ")
								.append(" ( ")
									.append(" exam.examLimit > ")
										.append(" (SELECT COUNT(examResult.examResultId) ")
											.append(" FROM ExamResult examResult")
											.append(" WHERE examResult.examId = exam.examId ")
											.append(" AND examResult.username = ? ")
										.append(" ) ")
								.append(" ) ")
							.append(" AND ( exam.endDate>? OR exam.endDate is null) ")
							.append(" AND ( exam.isCalScore = 0)");
		Object[] params = {
				SecurityUtils.getUsername(),
				true,
				SecurityUtils.getUsername(),
				SecurityUtils.getUsername(),
				new Date()
		};
		return basicFinderService.find(queryString.toString(), params);
	}
}
