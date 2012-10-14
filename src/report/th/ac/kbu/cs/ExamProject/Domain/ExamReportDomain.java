package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class ExamReportDomain extends ExamReportPrototype{
	
	@Autowired
	private StudentTeacherService studentTeacherService;
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	private void validateGetExamResult(){
		if(BeanUtils.isNull(this.getExamId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public CoreGrid<HashMap<String, Object>> getExamDetailTable(
			ExamResultReportCoreGridManager gridManager) {
		this.validateGetExamResult();
		return gridManager.search(this, SecurityUtils.getUsername());
	}

	public Exam getExamDetail() {
		this.validateGetExamResult();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Exam.class,"exam");
		criteria.createAlias("exam.course", "course");
		criteria.add(Restrictions.eq("exam.examId",this.getExamId()));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
}
