package th.ac.kbu.cs.ExamProject.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import th.ac.kbu.cs.ExamProject.Entity.ExamResult;

public interface DoExamService {
	Serializable saveExamResult(ExamResult examResult);
	void createExamResult(ExamResult examResult,List<HashMap<String, Object>> questionData);
}
