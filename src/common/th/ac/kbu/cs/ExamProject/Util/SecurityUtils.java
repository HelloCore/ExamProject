package th.ac.kbu.cs.ExamProject.Util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Security.Service.UserDetails;

public class SecurityUtils {
	public SecurityUtils(){
		
	}
	private static SecurityUtils INSTANCE = new SecurityUtils();
	
	public static Authentication getAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public static User getUser(){
		Authentication authen = SecurityUtils.getAuthentication();
		User user = null;
		if(BeanUtils.isNotNull(authen)){
			UserDetails userDetails = (UserDetails) authen.getPrincipal();
			user = userDetails.getUser();
		}
		return user;
	}
	public static String getTypeDesc(){
		User user = SecurityUtils.getUser();
		String typeDesc = null;
		if(BeanUtils.isNotNull(user)){
			typeDesc = user.getAuthority().getTypeDesc();
		}
		return typeDesc;
	}
	public static String getUsername(){
		User user = SecurityUtils.getUser();
		String username = null;
		if(BeanUtils.isNotNull(user)){
			username = user.getUsername();
		}
		return username;
	}
	
}
