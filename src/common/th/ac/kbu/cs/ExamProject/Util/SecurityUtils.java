package th.ac.kbu.cs.ExamProject.Util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Security.Service.UserDetails;

public class SecurityUtils {
	public SecurityUtils(){
		
	}
	private static SecurityUtils INSTANCE = new SecurityUtils();
	
	public static User getUser(){
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if(BeanUtils.isNotNull(authen)){
			UserDetails userDetails = (UserDetails) authen.getPrincipal();
			user = userDetails.getUser();
		}
		return user;
	}
	
	public static String getUsername(){
		User user = SecurityUtils.getUser();
		return user.getUsername();
	}
}
