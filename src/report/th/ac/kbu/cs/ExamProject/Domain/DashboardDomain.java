package th.ac.kbu.cs.ExamProject.Domain;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.Course;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;

@Configurable
public class DashboardDomain extends DashboardPrototype{
	@Autowired
	private BasicFinderService basicFinderService;
	
	public Course getCourse(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Course.class,"course");
		criteria.add(Restrictions.eq("course.courseId", this.getCourseId()));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public List<Object[]> getExamData(){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
						.append(" questionGroup.questionGroupId, ")
						.append(" questionGroup.questionGroupName, ")
						.append(" SUM(answer.answerScore), ")
						.append(" COUNT(question.questionId), ")
						.append(" (SUM(answer.answerScore)*100)/COUNT(question.questionId) ")
					.append(" FROM ")
						.append(" ExamResultAnswer examResultAnswer ")
					.append(" LEFT JOIN ")
						.append(" examResultAnswer.answer answer ")
					.append(" JOIN ")
						.append(" examResultAnswer.question question ")
					.append(" JOIN ")
							.append(" question.questionGroup questionGroup ")
					.append(" WHERE ")
							.append(" questionGroup.courseId = ? ")
					.append(" GROUP BY ")
							.append(" questionGroup.questionGroupId ")
					.append(" ORDER BY ")
							.append(" questionGroup.questionGroupName");
		return this.basicFinderService.find(queryString.toString(), this.getCourseId());
	}
}
