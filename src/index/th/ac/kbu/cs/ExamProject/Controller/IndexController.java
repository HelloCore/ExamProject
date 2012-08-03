package th.ac.kbu.cs.ExamProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping(value="/main/index.html")
	public ModelMap init(ModelMap modelMap){
		return modelMap;
	}
}
