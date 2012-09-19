package th.ac.kbu.cs.ExamProject.Service;

import java.util.List;

import th.ac.kbu.cs.ExamProject.Entity.Register;

public interface RegisterService {
	void acceptSection(List<Register> registers);
	void rejectSection(List<Register> registers);
}
