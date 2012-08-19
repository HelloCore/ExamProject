package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.ExamResult;
import th.ac.kbu.cs.ExamProject.Entity.ExamResultAnswer;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.DoExamService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Service
public class DoExamServiceImpl implements DoExamService{

	@Autowired
	private BasicEntityService basicEntityService;
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	public Serializable saveExamResult(ExamResult examResult) {
		return basicEntityService.save(examResult);
	}

	@Transactional(rollbackFor=Exception.class)
	public void createExamResult(ExamResult examResult,List<HashMap<String, Object>> questionData) {
		Long examResultId = BeanUtils.toLong(this.saveExamResult(examResult));
		List<ExamResultAnswer> examResultAnswerList = new ArrayList<ExamResultAnswer>();
		for(HashMap<String, Object> question : questionData){
			ExamResultAnswer examResultAnswer = new ExamResultAnswer();
			examResultAnswer.setExamResultId(examResultId);
			examResultAnswer.setQuestionId(BeanUtils.toLong(question.get("questionId")));
			examResultAnswerList.add(examResultAnswer);
		}
		basicEntityService.save(examResultAnswerList);
	}

}
