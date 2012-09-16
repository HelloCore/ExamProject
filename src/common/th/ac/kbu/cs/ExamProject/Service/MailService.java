package th.ac.kbu.cs.ExamProject.Service;

public interface MailService {
	void sendMail(String from,String to,String subject,String message);
}
