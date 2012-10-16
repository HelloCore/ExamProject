package th.ac.kbu.cs.ExamProject.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Controller
public class LoginController {
	

	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/login.html",method=RequestMethod.GET)
	public ModelMap init(@RequestParam(value="target",required=false) String target
							,@RequestParam(value="error",required=false) String error
							,@RequestParam(value="studentId",required=false) String studentId
							,ModelMap modelMap){
		modelMap.addAttribute("target",target);
		if(BeanUtils.isNotEmpty(error)){
			modelMap.addAttribute("error",true);
		}
		modelMap.addAttribute("studentId", studentId);
		return modelMap;
	}
	
	@RequestMapping(value="/main/keepAlive.html")
	public @ResponseBody String keepAlive(){
		return "OK";
	}

	
}
