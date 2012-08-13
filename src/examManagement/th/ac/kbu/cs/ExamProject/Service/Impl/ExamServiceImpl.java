package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamQuestionGroup;
import th.ac.kbu.cs.ExamProject.Entity.ExamSection;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.ExamService;

@Service
public class ExamServiceImpl implements ExamService{

	@Autowired
	private BasicEntityService basicEntityService;
	
	@Transactional
	public Serializable saveExam(Exam exam) {
		return basicEntityService.save(exam);
	}
	
	@Transactional
	public void saveExamSection(Long examId, List<ExamSection> examSections) {
		List<ExamSection> newList = new ArrayList<ExamSection>();
		for(ExamSection examSection : examSections){
			examSection.setExamId(examId);
			newList.add(examSection);
		}
		basicEntityService.save(newList);
	}
	
	@Transactional
	public void saveExamQuestionGroup(Long examId,
			List<ExamQuestionGroup> examQuestionGroups) {
		List<ExamQuestionGroup> newList = new ArrayList<ExamQuestionGroup>();
		for(ExamQuestionGroup examQuestionGroup : examQuestionGroups){
			examQuestionGroup.setExamId(examId);
			newList.add(examQuestionGroup);
		}
		basicEntityService.save(newList);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void addExam(Exam exam, List<ExamSection> examSections,List<ExamQuestionGroup> examQuestionGroups) {
		Long examId = (Long) this.saveExam(exam);
		this.saveExamSection(examId, examSections);
		this.saveExamQuestionGroup(examId, examQuestionGroups);
	}

}
