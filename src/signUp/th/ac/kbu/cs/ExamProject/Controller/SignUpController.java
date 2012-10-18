package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.SignUpDomain;

@Controller
public class SignUpController {
	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/signUp.html")
	public ModelMap init(ModelMap modelMap,SignUpDomain domain){
		return modelMap;
	}
	
	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/signUp.html",method=RequestMethod.POST)
	public void signUp(@ModelAttribute SignUpDomain domain,HttpServletResponse response,HttpServletRequest request){
		domain.signUp();
	}
	
	@RequestMapping(value="/main/activeUser.html",method=RequestMethod.GET)
	public ModelMap activeUserPage(@RequestParam(value="studentId",required=false) String studentId,ModelMap modelMap,HttpServletResponse response,HttpServletRequest request){
		modelMap.addAttribute("studentId", studentId);
		return modelMap;
	}

	@RequestMapping(value="/main/activeUser.html",method=RequestMethod.POST)
	public void activeUser(@ModelAttribute SignUpDomain domain,HttpServletResponse response,HttpServletRequest request){
		domain.activeUser();
	}

	@RequestMapping(value="/main/requestActiveCode.html",method=RequestMethod.GET)
	public ModelMap requestActiveCodePage(@RequestParam(value="studentId",required=false) String studentId,ModelMap modelMap,HttpServletResponse response,HttpServletRequest request){
		modelMap.addAttribute("studentId", studentId);
		return modelMap;
	}

	@RequestMapping(value="/main/requestActiveCode.html",method=RequestMethod.POST)
	public void requestActiveCode(@ModelAttribute SignUpDomain domain,HttpServletResponse response,HttpServletRequest request){
		domain.requestActiveCode();
	}

	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/main/signUpTeacher.html")
	public ModelMap initSignUpTeacher(ModelMap modelMap,SignUpDomain domain){
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/main/signUpTeacher.html",method=RequestMethod.POST)
	public void signUpTeacher(@ModelAttribute SignUpDomain domain,HttpServletResponse response,HttpServletRequest request){
		domain.signUpTeacher();
	}

	
}
