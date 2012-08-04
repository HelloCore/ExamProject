package th.ac.kbu.cs.ExamProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	@RequestMapping(value="/main/login.html")
	public ModelMap init(ModelMap modelMap){
		return modelMap;
	}

	 @RequestMapping(value="/main/loginFail.html",method=RequestMethod.GET)
	 public ModelMap loginError(ModelMap model){
		 model.addAttribute("error","true");
		 return model;
	 }
}
