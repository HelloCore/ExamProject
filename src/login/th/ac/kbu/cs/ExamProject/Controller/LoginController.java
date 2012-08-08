package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	

//	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/login.html",method=RequestMethod.GET)
	public ModelMap init(@RequestParam(value="target",required=false) String target,ModelMap modelMap){
		modelMap.addAttribute("target",target);
		return modelMap;
	}
	
//	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/loginFail.html",method=RequestMethod.GET)
	public ModelMap loginError(ModelMap model){
		model.addAttribute("error","true");
		return model;
	}
	
//	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/loginTimeout.html")
	public ModelMap loginTimeOut(ModelMap model,HttpServletResponse response){
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return model;
	}
	
	@RequestMapping(value="/main/keepAlive.html")
	public @ResponseBody String keepAlive(){
		return "OK";
	}
}
