package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;

@Controller
public class ExamController {

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/selectExam.html" ,method=RequestMethod.GET)
	public ModelMap initSelectExam(HttpServletRequest request){
		return new ModelMap();
	}
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/doExam.html" ,method=RequestMethod.GET)
	public ModelMap doSelectExam(HttpServletRequest request){
//	       String ip = request.getHeader("X-Forwarded-For");  
//	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//	           ip = request.getHeader("Proxy-Client-IP");  
//	       }  
//	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//	           ip = request.getHeader("WL-Proxy-Client-IP");  
//	       }  
//	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//	           ip = request.getHeader("HTTP_CLIENT_IP");  
//	       }  
//	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//	           ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
//	       }  
//	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//	           ip = request.getRemoteAddr();  
//	       }  
//	       System.out.println(ip);
		return new ModelMap();
	}
	
}
