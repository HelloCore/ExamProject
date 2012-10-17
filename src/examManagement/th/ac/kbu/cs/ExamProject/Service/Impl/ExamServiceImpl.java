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
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

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
		if(exam.getIsCalScore()){
			this.saveExamSection(examId, examSections);
		}
		this.saveExamQuestionGroup(examId, examQuestionGroups);
	}

	@Transactional(rollbackFor=Exception.class)
	public List<ExamQuestionGroup> updateExamQuestionGroup(
			List<ExamQuestionGroup> examQuestionGroupList,
			List<ExamQuestionGroup> deletedExamQuestionGroupList ) {
		Long examQuestionGroupId;
		if(BeanUtils.isNotEmpty(deletedExamQuestionGroupList)){
			basicEntityService.deleteAll(deletedExamQuestionGroupList);
		}
		if(BeanUtils.isNotEmpty(examQuestionGroupList)){
			for(ExamQuestionGroup examQuestionGroup : examQuestionGroupList){
				if(BeanUtils.isNotNull(examQuestionGroup.getExamQuestionGroupId())){
					basicEntityService.update(examQuestionGroup);
				}else{
					examQuestionGroupId = BeanUtils.toLong(basicEntityService.save(examQuestionGroup));
					examQuestionGroup.setExamQuestionGroupId(examQuestionGroupId);
				}
			}
		}
		return examQuestionGroupList;
	}

	public List<ExamSection> updateExamSection(
			List<ExamSection> savedExamSection,
			List<ExamSection> deletedExamSection) {
		Long examSectionId;
		if(BeanUtils.isNotEmpty(deletedExamSection)){
			basicEntityService.deleteAll(deletedExamSection);
		}
		if(BeanUtils.isNotEmpty(savedExamSection)){
			for(ExamSection examSection : savedExamSection){
				examSectionId = BeanUtils.toLong(basicEntityService.save(examSection));
				examSection.setExamSectionId(examSectionId);
			}
		}
		return savedExamSection;
	}

	public void updateExam(Exam exam) {
		basicEntityService.update(exam);
	}

}
