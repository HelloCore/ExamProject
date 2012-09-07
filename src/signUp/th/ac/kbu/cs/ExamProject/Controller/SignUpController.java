package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import th.ac.kbu.cs.ExamProject.Domain.SignUpDomain;
import th.ac.kbu.cs.ExamProject.Exception.DataDuplicateException;
import th.ac.kbu.cs.ExamProject.Exception.SignUpException;

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
	

	@ExceptionHandler(DataDuplicateException.class)
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	public @ResponseBody String dataDuplicateException(DataDuplicateException ex,HttpServletRequest request,HttpServletResponse response){
		return ex.getMessage();
	}

	@ExceptionHandler(SignUpException.class)
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	public @ResponseBody String signUpException(SignUpException ex,HttpServletRequest request,HttpServletResponse response){
		return ex.getMessage();
	}
	
}
