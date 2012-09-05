package th.ac.kbu.cs.ExamProject.Domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Service.MailService;

@Configurable
public class SignUpDomain {
	
	@Autowired
	private MailService mailService;
	
	public void testEmail(){
		mailService.sendMail("noreply.examproject@gmail.com", "aukwat@hotmail.com", "testing", "testingasdasdasd email");
	}
}
