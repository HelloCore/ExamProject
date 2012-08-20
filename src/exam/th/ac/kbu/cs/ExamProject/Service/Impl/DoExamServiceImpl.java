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
import th.ac.kbu.cs.ExamProject.Entity.ExamTempTable;
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
	public Long createExamResult(ExamResult examResult,List<HashMap<String, Object>> questionData) {
		Long examResultId = BeanUtils.toLong(this.saveExamResult(examResult));
		Long examResultAnswerId ;
		List<ExamResultAnswer> examResultAnswerList = new ArrayList<ExamResultAnswer>();
		
		Integer ordinal = 0;
		for(HashMap<String, Object> question : questionData){
			ExamResultAnswer examResultAnswer = this.toExamResultAnswer(examResultId, question);
			examResultAnswer.setOrdinal(ordinal);
			examResultAnswerId = BeanUtils.toLong(basicEntityService.save(examResultAnswer));
			examResultAnswer.setExamResultAnswerId(examResultAnswerId);
			examResultAnswerList.add(examResultAnswer);
			ordinal++;
		}
		return examResultId;
	}

	@SuppressWarnings("unchecked")
	private ExamResultAnswer toExamResultAnswer(Long examResultId,HashMap<String,Object> question){
		ExamResultAnswer examResultAnswer = new ExamResultAnswer();
		examResultAnswer.setExamResultId(examResultId);
		examResultAnswer.setQuestionId(BeanUtils.toLong(question.get("questionId")));
		
		
		List<HashMap<String, Object>> answerData = (List<HashMap<String, Object>>) question.get("answerData");
		List<Long> answerList = new ArrayList<Long>();
		for(HashMap<String, Object> answer : answerData){
			answerList.add(BeanUtils.toLong(answer.get("answerId")));
		}
		examResultAnswer.setAnswerId1(answerList.get(0));
		examResultAnswer.setAnswerId2(answerList.get(1));
		examResultAnswer.setAnswerId3(answerList.get(2));
		examResultAnswer.setAnswerId4(answerList.get(3));
		return examResultAnswer;
	}
	
	public void updateExamResultAnswer(ExamResultAnswer examResultAnswer) {
		basicEntityService.update(examResultAnswer);
	}

	@Transactional(rollbackFor=Exception.class)
	public void updateExamResult(ExamResult examResult,
			List<ExamResultAnswer> examResultAnswers) {
		basicEntityService.update(examResult);
		if(BeanUtils.isNotEmpty(examResultAnswers)){
			basicEntityService.update(examResultAnswers);
		}
	}

	public void insertExamTempTable(List<ExamTempTable> examTempTableList) {
		basicEntityService.save(examTempTableList);
	}

	@Transactional(rollbackFor=Exception.class)
	public void setCompleteExamFromSelect(ExamResult examResult,Boolean isExpired){
		basicEntityService.update(examResult);
		if(isExpired){
			String queryString = "UPDATE ExamResultAnswer examResultAnswer SET examResultAnswer.answerId=null WHERE examResultAnswer.examResultId = ?";
			basicEntityService.bulkUpdate(queryString, examResult.getExamResultId());
		}
	}
}
