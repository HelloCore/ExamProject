package th.ac.kbu.cs.ExamProject.Service.Impl;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import th.ac.kbu.cs.ExamProject.Service.MailService;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String from, String to, String subject, String msg) {
		MimeMessageHelper message;
		try {
			message = new MimeMessageHelper(javaMailSender.createMimeMessage(), true,"UTF-8");
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(msg);
			javaMailSender.send(message.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
