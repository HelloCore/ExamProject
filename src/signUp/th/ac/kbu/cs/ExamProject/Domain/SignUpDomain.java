package th.ac.kbu.cs.ExamProject.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.MailService;
import th.ac.kbu.cs.ExamProject.Service.SignUpService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Configurable
public class SignUpDomain extends SignUpPrototype{
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SignUpService signUpService;
	
	@Autowired
	private BasicFinderService basicFinderService;
	public void validateStudentIdAndEmail(String studentId,String email){
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class,"user");
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.or(Restrictions.eq("user.username", studentId),Restrictions.eq("user.email",email)));
		
		Long rows = basicFinderService.findUniqueByCriteria(criteria);
		if(rows >= 1L){
			throw new CoreException(CoreExceptionMessage.DUPLICATE_STUDENT_ID_OR_EMAIL);
		}
	}
	private void validateData(){
		this.validateEmptyData();
		this.validateYear(this.getStudentId());
		this.validateFaculty(this.getStudentId());
		this.validateStudentIdAndEmail(this.getStudentId(), this.getEmail());
	}
	private void validateEmptyData(){
		if(BeanUtils.isEmpty(this.getStudentId())
				|| BeanUtils.isEmpty(this.getPassword())
				|| BeanUtils.isEmpty(this.getRePassword())
				|| BeanUtils.isEmpty(this.getFirstName())
				|| BeanUtils.isEmpty(this.getLastName())
				|| BeanUtils.isEmpty(this.getEmail())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
			
	}
	private void validateYear(String studentId){
		String yearStr = studentId.substring(0, 2);
		Integer year = BeanUtils.toInteger(yearStr);
		if(year<50 || year>65){
			throw new CoreException(CoreExceptionMessage.INVALID_YEAR);
		}
	}
	
	private void validateFaculty(String studentId){
		String facultyStr = studentId.substring(2,7);
		if(!facultyStr.equalsIgnoreCase("07024")){
			throw new CoreException(CoreExceptionMessage.NOT_IN_FACULTY);
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
		mailService.sendMail("noreply@core-serv.com", email, "ExamProject Active Code", "รหัสยืนยันการใช้งานของคุณคือ: "+activeCode);
	}

	private void validateDataUpdate(){
		if(BeanUtils.isEmpty(this.getStudentId())
				|| BeanUtils.isEmpty(this.getActiveCode())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	public void activeUser() {
		validateDataUpdate();
		User user = this.getUser(this.getStudentId());
		if(BeanUtils.isNull(user)){
			throw new CoreException(CoreExceptionMessage.STUDENT_NOT_FOUND);
		}
		
		if(user.getType() != 4){
			throw new CoreException(CoreExceptionMessage.CANT_ACTIVE_ANYMORE);
		}
		
		if(user.getActiveStr().equals(this.getActiveCode())){
			signUpService.upgradeUserType(user);
		}else{
			throw new CoreException(CoreExceptionMessage.INVALID_ACTIVE_CODE);
		}
		
	}
	
	private void validateRequestActiveCodeData(){
		if(BeanUtils.isEmpty(this.getStudentId())
				|| BeanUtils.isEmpty(this.getEmail())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	public void requestActiveCode() {
		validateRequestActiveCodeData();
		User user = this.getUser(this.getStudentId());
		
		if(BeanUtils.isNull(user)){
			throw new CoreException(CoreExceptionMessage.STUDENT_NOT_FOUND);
		}
		
		if(user.getType() != 4){
			throw new CoreException(CoreExceptionMessage.CANT_ACTIVE_ANYMORE);
		}
		
		if(user.getEmail().equals(this.getEmail())){
			this.sendActiveEmail(this.getEmail(), user.getActiveStr());
		}else{
			throw new CoreException(CoreExceptionMessage.STUDENT_ID_AND_EMAIL_NOT_MATCH);
		}
	}
	

	public User getUser(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class, "users");
		criteria.add(Restrictions.eq("users.username", username));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
}
