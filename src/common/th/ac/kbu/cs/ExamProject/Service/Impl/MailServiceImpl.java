package th.ac.kbu.cs.ExamProject.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import th.ac.kbu.cs.ExamProject.Service.MailService;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private MailSender mailSender;

	public void sendMail(String from, String to, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}
	
}
