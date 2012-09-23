package th.ac.kbu.cs.ExamProject.Service.Impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Exception.DataDuplicateException;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService{

	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	public void signUp(User user){
		try{
			basicEntityService.save(user);
		}catch(DataIntegrityViolationException ex){
			String message = ex.getRootCause().getMessage();
			Integer length = message.length();
			
			if(message.substring(length-7,length).equals("'EMAIL'")){
				throw new DataDuplicateException("Email is duplicate");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public User getUser(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class, "users");
		criteria.add(Restrictions.eq("users.username", username));
		return basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public void upgradeUserType(User user){
		user.setType(3);
		basicEntityService.update(user);
	}
	
	public void validateStudentIdAndEmail(String studentId,String email){
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class,"user");
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.or(Restrictions.eq("user.username", studentId),Restrictions.eq("user.email",email)));
		
		Long rows = basicFinderService.findUniqueByCriteria(criteria);
		if(rows >= 1L){
			throw new DataDuplicateException("studentId or email is duplicate");
		}
		
	}
	
}
