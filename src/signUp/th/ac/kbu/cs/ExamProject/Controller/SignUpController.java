package th.ac.kbu.cs.ExamProject.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import th.ac.kbu.cs.ExamProject.Domain.SignUpDomain;

@Controller
public class SignUpController {
	
	
	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/signUp.html")
	public ModelMap init(ModelMap modelMap,SignUpDomain domain){
		domain.testEmail();
		return modelMap;
	}
}
