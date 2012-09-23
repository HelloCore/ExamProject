package th.ac.kbu.cs.ExamProject.Controller;

import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.RegisterDomain;
import th.ac.kbu.cs.ExamProject.Exception.MainException;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Controller
public class RegisterController {

	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	@RequestMapping(value="/member/register.html" ,method=RequestMethod.GET)
	public ModelMap init(ModelMap modelMap,HttpServletRequest request){
		return modelMap;
	}
	
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	@RequestMapping(value="/member/register.html",method=RequestMethod.POST,params={"method=register"})
	public void register(@ModelAttribute RegisterDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.register(SecurityUtils.getUsername());
	}
	
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	@RequestMapping(value="/member/register.html",method=RequestMethod.POST,params={"method=getRegisterTable"})
	public @ResponseBody List<HashMap<String,Object>> getRegisterTable(@ModelAttribute RegisterDomain domain,HttpServletRequest request,HttpServletResponse response){
		return domain.getRegisterTable(SecurityUtils.getUsername());
	}
	
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	@RequestMapping(value="/member/register.html",method=RequestMethod.POST,params={"method=cancelRegister"})
	public void cancelRegister(@ModelAttribute RegisterDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.cancelRegister(SecurityUtils.getUsername());
	}
	
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	@RequestMapping(value="/member/register.html",method=RequestMethod.POST,params={"method=changeSection"})
	public void changeSection(@ModelAttribute RegisterDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.changeSection(SecurityUtils.getUsername());
	}
	
	@ExceptionHandler(MainException.class)
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	public @ResponseBody String mainException(MainException ex,HttpServletRequest request,HttpServletResponse response){
		return ex.getMessage();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	public @ResponseBody String exception(Exception ex,HttpServletRequest request,HttpServletResponse response){
		return ex.getMessage();
	}
}
