package th.ac.kbu.cs.ExamProject.Controller;

import java.util.HashMap;

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

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.QuestionGroupCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.QuestionGroupDomain;

@Controller
public class QuestionGroupManagementController {
	
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/questionGroup.html" ,method=RequestMethod.GET)
	public ModelMap init(ModelMap modelMap,HttpServletRequest request){
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/questionGroup.html" ,method=RequestMethod.POST)
	public @ResponseBody CoreGrid<HashMap<String,Object>> search(@ModelAttribute QuestionGroupDomain domain,@ModelAttribute QuestionGroupCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		return domain.search(gridManager);
	}
	
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/questionGroup/save.html" ,method=RequestMethod.POST)
	public void save(@ModelAttribute QuestionGroupDomain domain,@ModelAttribute QuestionGroupCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		domain.save(gridManager);
	}
	
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/questionGroup/delete.html" ,method=RequestMethod.POST)
	public void delete(@ModelAttribute QuestionGroupDomain domain,@ModelAttribute QuestionGroupCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		domain.delete(gridManager);
	}
	

}
