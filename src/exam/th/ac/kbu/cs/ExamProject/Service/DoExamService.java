package th.ac.kbu.cs.ExamProject.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateCallback;

import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.ExamResultAnswer;
import th.ac.kbu.cs.ExamProject.Entity.ExamTempTable;

public interface DoExamService {
	Serializable saveExamResult(ExamResult examResult);
	Long createExamResult(ExamResult examResult,List<HashMap<String, Object>> questionData);
	Long createExamResultObj(ExamResult examResult,List<Object[]> questionData);
	void updateExamResultAnswer(ExamResultAnswer examResultAnswer);
	void updateExamResult(ExamResult examResult,List<ExamResultAnswer> examResultAnswers,Boolean isExpired,Boolean isAutoSave);
	void insertExamTempTable(List<ExamTempTable> examTempTableList);
	void setCompleteExamFromSelect(ExamResult examResult,Boolean isExpired);
	<T> T execute(HibernateCallback<T> action);
}