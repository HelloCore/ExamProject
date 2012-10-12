package th.ac.kbu.cs.ExamProject.Service;

import th.ac.kbu.cs.ExamProject.Entity.User;

public interface SignUpService {
	void signUp(User user);
	void upgradeUserType(User user);
}
