package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.MailService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Configurable
public class ForgotPasswordDomain extends ForgotPasswordPrototype{

	@Autowired
	private MailService mailService;
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	private void validateForgotPassword(){
		if(BeanUtils.isEmpty(this.getEmail())
				|| BeanUtils.isEmpty(this.getStudentId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	private User toEntity(){
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class,"user");
		criteria.add(Restrictions.eq("user.username", this.getStudentId()));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	public void forgotPassword(){
		this.validateForgotPassword();
		User user = this.toEntity();
		if(BeanUtils.isNull(user)){
			throw new CoreException(CoreExceptionMessage.INVALID_DATA);
		}
		if(user.getEmail().equals(this.getEmail())){
			PasswordEncoder encoder = new Md5PasswordEncoder();
			StringBuilder tempActiveCode = new StringBuilder();
			tempActiveCode.append(user.getEmail()).append(user.getPasswords()).append(user.getFirstName()).append(user.getLastName()).append(Math.random());
			String activStrHash = encoder.encodePassword(tempActiveCode.toString(), null).substring(0,6);
			
			user.setActiveStr(activStrHash);
			
			this.mailService.sendMail("noreply@core-serv.com", user.getEmail(), "E-learning Forgot Password", "รหัสยืนยันในการเปลี่ยนรหัสผ่านของคุณคือ: "+activStrHash);
			this.basicEntityService.update(user);
		}else{
			throw new CoreException(CoreExceptionMessage.STUDENT_ID_AND_EMAIL_NOT_MATCH);
		}
	}

	private void validateChangePassword(){
		if(BeanUtils.isEmpty(this.getNewPassword())
				|| BeanUtils.isEmpty(this.getStudentId())
				|| BeanUtils.isEmpty(this.getRetypePassword())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
		if(!this.getNewPassword().equals(this.getRetypePassword())){
			throw new CoreException(CoreExceptionMessage.PASSWORD_IS_NOT_MATCH);
		}
	}
	public void changePassword() {
		this.validateChangePassword();
		User user = this.toEntity();
		if(BeanUtils.isNull(user)){
			throw new CoreException(CoreExceptionMessage.INVALID_DATA);
		}
		if(user.getActiveStr().equals(this.getActiveCode())){
			PasswordEncoder encoder = new Md5PasswordEncoder();
			user.setPassword(encoder.encodePassword(this.getNewPassword(), null));
			user.setPasswords(this.getNewPassword());
			user.setActiveStr(null);
			this.basicEntityService.update(user);
		}else{
			throw new CoreException(CoreExceptionMessage.INVALID_ACTIVE_CODE);
		}
		
	}
}
