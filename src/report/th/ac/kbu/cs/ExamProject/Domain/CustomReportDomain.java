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

import th.ac.kbu.cs.ExamProject.Bean.AssignmentData;
import th.ac.kbu.cs.ExamProject.Bean.ExamData;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentSection;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Entity.ExamSection;
import th.ac.kbu.cs.ExamProject.Entity.Section;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class CustomReportDomain extends CustomReportPrototype{
	public static final Integer CALCULATE_SCORE_BY_LAST_EXAM = 1;
	public static final Integer CALCULATE_SCORE_BY_MAX_SCORE = 2;
	public static final Integer CALCULATE_SCORE_BY_AVG_SCORE = 3;
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	private void validateGetExamList(){
		if(BeanUtils.isNull(this.getSectionId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	
	public List<HashMap<String,Object>> getExamList(){
		this.validateGetExamList();
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamSection.class,"examSection");
		criteria.createAlias("examSection.exam", "exam");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("exam.examId"),"examId");
		projectionList.add(Projections.property("exam.startDate"),"startDate");
		projectionList.add(Projections.property("exam.endDate"),"endDate");
		projectionList.add(Projections.property("exam.examHeader"),"examHeader");
		projectionList.add(Projections.property("exam.maxScore"),"maxScore");
		
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("examSection.sectionId", this.getSectionId()));
		criteria.add(Restrictions.eq("exam.isCalScore", true));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return this.basicFinderService.findByCriteria(criteria);
	}
	
	
	private void validateGetAssignmentList(){
		if(BeanUtils.isNull(this.getSectionId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public List<HashMap<String,Object>> getAssignmentList(){
		this.validateGetAssignmentList();
		DetachedCriteria criteria = DetachedCriteria.forClass(AssignmentSection.class,"assignmentSection");
		criteria.createAlias("assignmentSection.assignmentTask", "assignmentTask");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("assignmentTask.assignmentTaskId"),"assignmentTaskId");
		projectionList.add(Projections.property("assignmentTask.assignmentTaskName"),"assignmentTaskName");
		projectionList.add(Projections.property("assignmentTask.startDate"),"startDate");
		projectionList.add(Projections.property("assignmentTask.endDate"),"endDate");
		projectionList.add(Projections.property("assignmentTask.isEvaluateComplete"),"isEvaluateComplete");
		projectionList.add(Projections.property("assignmentTask.maxScore"),"maxScore");
		

		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("assignmentSection.sectionId", this.getSectionId()));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return this.basicFinderService.findByCriteria(criteria);
	}
	
	
	public void validateViewCustomReportData(){
		if(BeanUtils.isEmpty(this.getCourseId())
				|| BeanUtils.isEmpty(this.getSectionId())
				|| BeanUtils.isEmpty(this.getScoreChoice())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
		if(BeanUtils.isEmpty(this.getAssignmentData()) && BeanUtils.isEmpty(this.getExamData())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);			
		}
	}
	
	public List<ExamData> getExamCustomList(){
		ObjectMapper mapper = new ObjectMapper();
		List<ExamData> examData = null;
		try{
			if(BeanUtils.isNotEmpty(this.getExamData())){
				examData = mapper.readValue(this.getExamData(), new TypeReference<ArrayList<ExamData>>() {});
			}
		}catch(Exception ex){
			throw new CoreException(new CoreExceptionMessage(ex.getMessage()));
		}
		return examData;
	}

	public Section getSection(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Section.class,"section");
		criteria.createAlias("section.course", "course");
		criteria.createAlias("section.masterSection", "masterSection");
		criteria.add(Restrictions.eq("section.sectionId", this.getSectionId()));
		
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public List<AssignmentData> getAssignmentCustomList(){
		ObjectMapper mapper = new ObjectMapper();
		List<AssignmentData> assignmentCustomList = null;
		try{
			if(BeanUtils.isNotEmpty(this.getAssignmentData())){
				assignmentCustomList = mapper.readValue(this.getAssignmentData(),new TypeReference<ArrayList<AssignmentData>>() {});
			}
		}catch(Exception ex){
			throw new CoreException(new CoreExceptionMessage(ex.getMessage()));
		}
	
		return assignmentCustomList;
	}
	
	
	public List<Object> getCustomData(List<ExamData> examData,List<AssignmentData> assignmentData){
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" SELECT ")
					.append(" user.USERNAME ")
					.append(" ,prefixName.PREFIX_NAME_TH ")
					.append(" ,user.FIRST_NAME ")
					.append(" ,user.LAST_NAME ");
		
		
		if(BeanUtils.isNotNull(examData)){
			for(ExamData examRecord : examData){
				if(this.getScoreChoice().equals(CustomReportDomain.CALCULATE_SCORE_BY_AVG_SCORE)){
//					sqlString.append(" ,AVG( examResult").append(examRecord.getExamId()).append(".EXAM_SCORE")
//					.append(" / examResult").append(examRecord.getExamId()).append(".NUM_OF_QUESTION )")
//					.append(" * ").append(examRecord.getExamScore());

					sqlString.append(" ,AVG( examResult").append(examRecord.getExamId()).append(".EXAM_SCORE)");
					
				}else if(this.getScoreChoice().equals(CustomReportDomain.CALCULATE_SCORE_BY_MAX_SCORE)){
//					sqlString.append(" ,MAX( examResult").append(examRecord.getExamId()).append(".EXAM_SCORE")
//					.append(" / examResult").append(examRecord.getExamId()).append(".NUM_OF_QUESTION )")
//					.append(" * ").append(examRecord.getExamScore());

					sqlString.append(" ,MAX( examResult").append(examRecord.getExamId()).append(".EXAM_SCORE)");
				}else if(this.getScoreChoice().equals(CustomReportDomain.CALCULATE_SCORE_BY_LAST_EXAM)){
//					sqlString.append(" ,( examResult").append(examRecord.getExamId()).append(".EXAM_SCORE")
//									.append(" / examResult").append(examRecord.getExamId()).append(".NUM_OF_QUESTION )")
//									.append(" * ").append(examRecord.getExamScore());
					sqlString.append(" , examResult").append(examRecord.getExamId()).append(".EXAM_SCORE ");
				}
			}
		}
		
		if(BeanUtils.isNotNull(assignmentData)){
			for(AssignmentData assignmentRecord : assignmentData){
				sqlString.append(" ,assignmentWork").append(assignmentRecord.getAssignmentTaskId()).append(".SCORE ");
			}
		}
		
		// prepare form statement
		sqlString.append(" FROM ")
					.append(" STUDENT_SECTION studentSection ")
					.append(" INNER JOIN USERS user ON user.USERNAME = studentSection.USERNAME ")
					.append(" INNER JOIN PREFIX_NAME prefixName ON user.PREFIX_NAME_ID = prefixName.PREFIX_NAME_ID ");

		if(BeanUtils.isNotNull(examData)){
			for(ExamData examRecord : examData){
				if(this.getScoreChoice().equals(CustomReportDomain.CALCULATE_SCORE_BY_AVG_SCORE) || this.getScoreChoice().equals(CustomReportDomain.CALCULATE_SCORE_BY_MAX_SCORE) ){
					sqlString.append(" LEFT JOIN EXAM_RESULT examResult").append(examRecord.getExamId())
						.append(" ON examResult").append(examRecord.getExamId()).append(".USERNAME = user.USERNAME")
						.append(" AND examResult").append(examRecord.getExamId()).append(".EXAM_ID=").append(examRecord.getExamId());
				}else if (this.getScoreChoice().equals(CustomReportDomain.CALCULATE_SCORE_BY_LAST_EXAM)){
					
					sqlString.append(" LEFT JOIN EXAM_RESULT examResult").append(examRecord.getExamId())
						.append(" ON examResult").append(examRecord.getExamId()).append(".USERNAME = user.USERNAME")
						.append(" AND examResult").append(examRecord.getExamId()).append(".EXAM_ID=").append(examRecord.getExamId())
					.append(" LEFT JOIN EXAM_RESULT examResultTemp").append(examRecord.getExamId())
						.append(" ON examResultTemp").append(examRecord.getExamId()).append(".USERNAME = user.USERNAME")
						.append(" AND examResultTemp").append(examRecord.getExamId()).append(".EXAM_ID=").append(examRecord.getExamId())
						.append(" AND examResult").append(examRecord.getExamId()).append(".EXAM_RESULT_ID<")
							.append(" examResultTemp").append(examRecord.getExamId()).append(".EXAM_RESULT_ID");
				}
			}
		}
		
		if(BeanUtils.isNotNull(assignmentData)){
			for(AssignmentData assignmentRecord : assignmentData){
				sqlString.append(" LEFT JOIN ASSIGNMENT_WORK assignmentWork").append(assignmentRecord.getAssignmentTaskId())
							.append(" ON assignmentWork").append(assignmentRecord.getAssignmentTaskId()).append(".SEND_BY = user.USERNAME ")
							.append(" AND assignmentWork").append(assignmentRecord.getAssignmentTaskId()).append(".ASSIGNMENT_TASK_ID = ").append(assignmentRecord.getAssignmentTaskId());
			}
		}
		
		// prepare where statement
		
		sqlString.append(" WHERE studentSection.SECTION_ID = ").append(this.getSectionId());
		if (this.getScoreChoice().equals(CustomReportDomain.CALCULATE_SCORE_BY_LAST_EXAM)){
			if(BeanUtils.isNotNull(examData)){
				for(ExamData examRecord : examData){
					sqlString.append(" AND examResultTemp").append(examRecord.getExamId()).append(".EXAM_RESULT_ID IS NULL ");
				}
			}
		}
		// prepare order statement
		
		sqlString.append(" GROUP BY user.USERNAME ORDER BY user.USERNAME ");
		
		final String queryString = sqlString.toString();
		
		List results = (List)this.basicEntityService.execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		
		return results;
	}
}


