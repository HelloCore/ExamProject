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
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class ChangeUserDataDomain extends ChangeUserDataPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	private void validateDataChangePassword(){
		if(BeanUtils.isEmpty(this.getOldPassword())
				|| BeanUtils.isEmpty(this.getNewPassword())
				|| BeanUtils.isEmpty(this.getRetypePassword())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}else if(!this.getRetypePassword().equals(this.getNewPassword())){
			throw new CoreException(CoreExceptionMessage.PASSWORD_IS_NOT_MATCH);
		}else if (this.getNewPassword().equals(this.getOldPassword())){
			throw new CoreException(CoreExceptionMessage.OLD_AND_NEW_PASSWORD_IS_DUPLICATE);
		}
	}
	
	private User getUserEntity(){
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class,"user");
		criteria.add(Restrictions.eq("user.username", SecurityUtils.getUsername()));
		
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public void changePassword(){
		this.validateDataChangePassword();
		PasswordEncoder encoder = new Md5PasswordEncoder();
		
		User user = this.getUserEntity();
		if(user.getPassword().equals(encoder.encodePassword(this.getOldPassword(), null))){
			user.setPasswords(this.getNewPassword());
			user.setPassword(encoder.encodePassword(this.getNewPassword(), null));
			
			this.basicEntityService.save(user);
		}else{
			throw new CoreException(CoreExceptionMessage.PASSWORD_IS_INVALID);
		}	
	}
	
	
	public User getUserData(String username){
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class,"user");
		criteria.add(Restrictions.eq("user.username",username));
		
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}
	
	private void validateEditPersonalData(){
		if(BeanUtils.isEmpty(this.getEmail())
				|| BeanUtils.isEmpty(this.getFirstName())
				|| BeanUtils.isEmpty(this.getLastName())
				|| BeanUtils.isEmpty(this.getPassword())
				|| BeanUtils.isEmpty(this.getPrefixName())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}		
	}
	
	private void validateUserData(User user){
		PasswordEncoder encoder = new Md5PasswordEncoder();
		if(!user.getPassword().equals(encoder.encodePassword(this.getPassword(), null))){
			throw new CoreException(CoreExceptionMessage.PASSWORD_IS_INVALID);
		}
	}

	public void editPersnoalData(String username) {
		this.validateEditPersonalData();
		User user = this.getUserData(username);
		
		this.validateUserData(user);
		user.setEmail(this.getEmail());
		user.setFirstName(this.getFirstName());
		user.setLastName(this.getLastName());
		user.setPrefixNameId(this.getPrefixName());
		
		this.basicEntityService.update(user);
		
	}
	
}
