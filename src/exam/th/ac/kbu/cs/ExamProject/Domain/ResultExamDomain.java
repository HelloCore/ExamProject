package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.ExamResultAnswer;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class ResultExamDomain extends DoExamPrototype {
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	private void validateBaseData(){
		if(BeanUtils.isEmpty(this.getExamResultId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	private void validateExamResultComplete(ExamResult examResult,Boolean isStudent){
		if(!examResult.getExamCompleted()){
			throw new CoreException(CoreExceptionMessage.EXAM_NOT_COMPLETE);
		}
		if(isStudent){
			if(!examResult.getUsername().equals(SecurityUtils.getUsername())){
				throw new CoreException(CoreExceptionMessage.PERMISSION_DENIED);
			}
		}
	}
	
	public List<HashMap<String,Object>> getResultData() {
		validateBaseData();
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResultAnswer.class,"examResultAnswer");
		criteria.createAlias("examResultAnswer.question", "question");
		criteria.createAlias("examResultAnswer.answer", "answer",Criteria.LEFT_JOIN);
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("question.questionText"),"questionText");
		projectionList.add(Projections.property("answer.answerScore"),"answerScore");
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("examResultAnswer.examResultId", this.getExamResultId()));
		criteria.addOrder(Order.asc("examResultAnswer.ordinal"));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
	}
	public ExamResult getExamResultData(Boolean isStudent){
		validateBaseData();
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamResult.class,"examResult");
		criteria.createAlias("examResult.exam", "exam");
		criteria.createAlias("exam.course", "course");
		criteria.add(Restrictions.eq("examResult.examResultId", this.getExamResultId()));
		ExamResult examResult = basicFinderService.findUniqueByCriteria(criteria);
		validateExamResultComplete(examResult,isStudent);
		return basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public CoreGrid<HashMap<String,Object>> searchStudent(ExamReportCoreGridManager gridManager){
		return gridManager.search(this,SecurityUtils.getUsername());
	}
	
	
}
