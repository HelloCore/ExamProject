package th.ac.kbu.cs.ExamProject.Service;

import th.ac.kbu.cs.ExamProject.Entity.User;

public interface SignUpService {
	User getUser(String username);
	void signUp(User user);
	void upgradeUserType(User user);
	void validateStudentIdAndEmail(String studentId,String email);
}
