package th.ac.kbu.cs.ExamProject.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService{
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	public void signUp(User user){
		try{
			basicEntityService.save(user);
		}catch(DataIntegrityViolationException ex){
			String message = ex.getRootCause().getMessage();
			Integer length = message.length();
			
			if(message.substring(length-7,length).equals("'EMAIL'")){
				throw new CoreException(CoreExceptionMessage.DUPLICATE_EMAIL);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void upgradeUserType(User user){
		user.setType(3);
		basicEntityService.update(user);
	}
	
	
	
}
