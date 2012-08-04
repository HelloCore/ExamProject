package th.ac.kbu.cs.ExamProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import th.ac.kbu.cs.ExamProject.Exception.NotNullableException;

@Controller
public class IndexController {
	@RequestMapping(value="/main/index.html")
	public ModelMap init(ModelMap modelMap){
		modelMap.addAttribute("strTest","55555");
		return modelMap;
	}
}
