package th.ac.kbu.cs.ExamProject.Service;

import java.io.Serializable;
import java.util.List;

import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamQuestionGroup;
import th.ac.kbu.cs.ExamProject.Entity.ExamSection;

public interface ExamService {
	Serializable saveExam(Exam exam);
	void saveExamSection(Long examId,List<ExamSection> examSections);
	void saveExamQuestionGroup(Long examId,List<ExamQuestionGroup> examQuestionGroups);
	void addExam(Exam exam,List<ExamSection> examSections,List<ExamQuestionGroup> examQuestionGroups);
	List<ExamQuestionGroup> updateExamQuestionGroup(List<ExamQuestionGroup> examQuestionGroupList,List<ExamQuestionGroup> deletedExamQuestionGroupList);
	List<ExamSection> updateExamSection(List<ExamSection> savedExamSection,List<ExamSection> deletedExamSection);
}
