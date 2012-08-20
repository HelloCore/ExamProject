package th.ac.kbu.cs.ExamProject.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.ExamResultAnswer;
import th.ac.kbu.cs.ExamProject.Entity.ExamTempTable;

public interface DoExamService {
	Serializable saveExamResult(ExamResult examResult);
	Long createExamResult(ExamResult examResult,List<HashMap<String, Object>> questionData);
	void updateExamResultAnswer(ExamResultAnswer examResultAnswer);
	void updateExamResult(ExamResult examResult,List<ExamResultAnswer> examResultAnswers);
	void insertExamTempTable(List<ExamTempTable> examTempTableList);
	void setCompleteExamFromSelect(ExamResult examResult,Boolean isExpired);
}