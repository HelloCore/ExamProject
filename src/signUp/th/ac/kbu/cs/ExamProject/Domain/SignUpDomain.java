package th.ac.kbu.cs.ExamProject.Domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Exception.ActiveCodeInvalidException;
import th.ac.kbu.cs.ExamProject.Exception.CantActiveAnymoreException;
import th.ac.kbu.cs.ExamProject.Exception.DataNotMatchException;
import th.ac.kbu.cs.ExamProject.Exception.NotFoundStudentException;
import th.ac.kbu.cs.ExamProject.Exception.NotInFacultyException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Exception.YearInvalidException;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.MailService;
import th.ac.kbu.cs.ExamProject.Service.SignUpService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Configurable
public class SignUpDomain extends SignUpPrototype{
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SignUpService signUpService;
	
	private void validateData(){
		this.validateYear(this.getStudentId());
		this.validateFaculty(this.getStudentId());
	}
	private void validateYear(String studentId){
		String yearStr = studentId.substring(0, 2);
		Integer year = BeanUtils.toInteger(yearStr);
		if(year<50 || year>65){
			throw new YearInvalidException("Year invalid!");
		}
	}
	
	private void validateFaculty(String studentId){
		String facultyStr = studentId.substring(2,7);
		if(!facultyStr.equalsIgnoreCase("07024")){
			throw new NotInFacultyException("Not in faculty!");
		}
	}
	
	private User toEntity(){
		PasswordEncoder encoder = new Md5PasswordEncoder();
		User user = new User();

		StringBuilder activeStr = new StringBuilder("");
		if(BeanUtils.isNotEmpty(this.getStudentId())){
			user.setUsername(this.getStudentId());
			activeStr.append(this.getStudentId());
		}
		if(BeanUtils.isNotEmpty(this.getPassword())){
			user.setPassword(encoder.encodePassword(this.getPassword(), null));
			activeStr.append(this.getPassword());
		}
		if(BeanUtils.isNotEmpty(this.getRePassword())){
			user.setPasswords(this.getRePassword());
			activeStr.append(this.getRePassword());
		}
		if(BeanUtils.isNotEmpty(this.getFirstName())){
			user.setFirstName(this.getFirstName());
			activeStr.append(this.getFirstName());
		}
		if(BeanUtils.isNotEmpty(this.getLastName())){
			user.setLastName(this.getLastName());
			activeStr.append(this.getLastName());
		}
		if(BeanUtils.isNotEmpty(this.getEmail())){
			user.setEmail(this.getEmail());
			activeStr.append(this.getEmail());
		}
		activeStr.append(Math.random());
		
		String activStrHash = encoder.encodePassword(activeStr.toString(), null);
		user.setActiveStr(activStrHash.substring(0,6));
		user.setFlag(true);
		user.setType(4);
		return user;
	}
	
	public void testEmail(){
		mailService.sendMail("noreply.examproject@gmail.com", "aukwat@hotmail.com", "testing", "testingasdasdasd email");
	}

	public void signUp() {
		this.validateData();
		User user = this.toEntity();
		signUpService.signUp(user);
		sendActiveEmail(user.getEmail(),user.getActiveStr());
	}
	
	private void sendActiveEmail(String email,String activeCode){
		mailService.sendMail("noreply.examproject@gmail.com", email, "ExamProject Active Code", "รหัสยืนยันการใช้งานของคุณคือ: "+activeCode);
	}

	private void validateDataUpdate(){
		if(BeanUtils.isEmpty(this.getStudentId())
				|| BeanUtils.isEmpty(this.getActiveCode())){
			throw new ParameterNotFoundException("parameter not found!");
		}
	}
	public void activeUser() {
		validateDataUpdate();
		User user = signUpService.getUser(this.getStudentId());
		if(BeanUtils.isNull(user)){
			throw new NotFoundStudentException("Student not found!");
		}
		
		if(user.getType() != 4){
			throw new CantActiveAnymoreException("Cant active anymore");
		}
		
		if(user.getActiveStr().equals(this.getActiveCode())){
			signUpService.upgradeUserType(user);
		}else{
			throw new ActiveCodeInvalidException("Active code invalid!");
		}
		
	}
	
	private void validateRequestActiveCodeData(){
		if(BeanUtils.isEmpty(this.getStudentId())
				|| BeanUtils.isEmpty(this.getEmail())){
			throw new ParameterNotFoundException("parameter not found!");
		}
	}
	public void requestActiveCode() {
		validateRequestActiveCodeData();
		User user = signUpService.getUser(this.getStudentId());
		
		if(BeanUtils.isNull(user)){
			throw new NotFoundStudentException("Student not found!");
		}
		
		if(user.getType() != 4){
			throw new CantActiveAnymoreException("Cant active anymore");
		}
		
		if(user.getEmail().equals(this.getEmail())){
			this.sendActiveEmail(this.getEmail(), user.getActiveStr());
		}else{
			throw new DataNotMatchException("Student id and email not match!");
		}
	}
	
}
