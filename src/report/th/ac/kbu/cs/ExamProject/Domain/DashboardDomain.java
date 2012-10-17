package th.ac.kbu.cs.ExamProject.Domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.orm.hibernate3.HibernateCallback;

import th.ac.kbu.cs.ExamProject.Entity.Course;
import th.ac.kbu.cs.ExamProject.Entity.ExamSection;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Configurable
public class DashboardDomain extends DashboardPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
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
					.append(" INNER JOIN examResultAnswer.examResult examResult ")
					.append(" INNER JOIN examResult.exam exam ")
					.append(" LEFT JOIN ")
						.append(" examResultAnswer.answer answer ")
					.append(" JOIN ")
						.append(" examResultAnswer.question question ")
					.append(" JOIN ")
							.append(" question.questionGroup questionGroup ")
					.append(" WHERE ")
							.append(" questionGroup.courseId = ? ")
							.append(" AND exam.isCalScore = ?")
					.append(" GROUP BY ")
							.append(" questionGroup.questionGroupId ")
					.append(" ORDER BY ")
							.append(" questionGroup.questionGroupName");
		return this.basicFinderService.find(queryString.toString(), new Object[]{this.getCourseId(),true});
	}
	
	public List<HashMap<String,Object>> getSectionList(){
		if(BeanUtils.isNull(this.getExamId())){
			throw new  CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(ExamSection.class,"examSection");
		criteria.createAlias("examSection.section", "section");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("section.sectionId"),"sectionId");
		projectionList.add(Projections.property("section.sectionName"),"sectionName");
		projectionList.add(Projections.property("section.sectionYear"),"sectionYear");
		projectionList.add(Projections.property("section.sectionSemester"),"sectionSemester");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("examSection.examId", this.getExamId()));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return this.basicFinderService.findByCriteria(criteria);
	}
	
	public List<ArrayList<Object>> getExamScoreData(List<HashMap<String,Object>> sectionList){
		if(BeanUtils.isNull(this.getExamId())){
			throw new  CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
		
		
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT ")
					.append(" examResult.EXAM_RESULT_ID ")
					.append(" ,examResult.NUM_OF_QUESTION ")
					.append(" ,examResult.EXAM_COUNT ")
					.append(" ,examResult.EXAM_USED_TIME ")
					.append(" ,examResult.EXAM_SCORE ")
					.append(" ,user.USERNAME ")
					.append(" ,user.FIRST_NAME ")
					.append(" ,user.LAST_NAME ")
					.append(" ,section.SECTION_NAME ")
					.append(" ,section.SECTION_YEAR ")
					.append(" ,section.SECTION_SEMESTER ")
					.append(" ,exam.MAX_SCORE ")
					
					.append(" FROM EXAM_RESULT examResult ")
					.append(" INNER JOIN EXAM exam ON exam.EXAM_ID = examResult.EXAM_ID ")
					.append(" INNER JOIN USERS user ON examResult.USERNAME = user.USERNAME ")
					.append(" INNER JOIN STUDENT_SECTION studentSection ON examResult.USERNAME = studentSection.USERNAME ")
					.append(" INNER JOIN SECTION section ON studentSection.SECTION_ID = section.SECTION_ID ")
					.append(" WHERE studentSection.SECTION_ID IN (SELECT examSection.SECTION_ID FROM EXAM_SECTION examSection WHERE examSection.EXAM_ID = ? ) ")
					.append(" AND examResult.EXAM_ID = ? ");
		
		final Long examId = this.getExamId();
		final String sqlString = queryString.toString();

		
		List results = (List)this.basicEntityService.execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sqlString).setParameter(0, examId).setParameter(1, examId).list();
			}
		});
		List<ArrayList<Object>> finalList = new ArrayList<ArrayList<Object>>();
		List<String> keyList = new ArrayList<String>();
		
		for(HashMap<String,Object> record : sectionList){
			keyList.add(record.get("sectionName").toString()+record.get("sectionSemester").toString()+record.get("sectionYear").toString());
		}
		
		Integer currentIndex;
		for(Object rawRecord : results){
			StringBuilder tooltipData = new StringBuilder();
			ArrayList<Object> strList = new ArrayList<Object>();
			Object[] record = (Object[]) rawRecord;
			strList.add(record[4]);
			currentIndex = keyList.indexOf(record[8].toString()+record[10].toString()+record[9].toString());
			for(int i=0;i<keyList.size();i++){
				if(currentIndex.equals(i)){
					strList.add(record[3]);
					tooltipData.append("รหัสนักศึกษา ")
									.append(record[5].toString())
								.append(" ชื่อ ")
									.append(record[6].toString())
									.append(" ")
									.append(record[7].toString())
								.append(" สอบครั้งที่ ")
									.append(record[2].toString())
								.append(" ใช้เวลาสอบ ")
									.append(record[3].toString())
								.append(" วินาที จำนวนคำถาม ")
									.append(record[1].toString())
								.append(" ข้อ ได้คะแนน ")
									.append(record[4].toString()).append(" / ").append(record[11].toString())
								.append(" คะแนน ");
					strList.add(tooltipData.toString());
				}else{
					strList.add(null);
					strList.add(null);
				}
			}
			finalList.add( strList);
		}
		
		return finalList;
	}
}
