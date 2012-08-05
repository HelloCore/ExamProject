package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.CourseComboBoxDomain;
import th.ac.kbu.cs.ExamProject.Domain.QuestionGroupComboBoxDomain;

@Controller
public class ComboBoxController {
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/courseComboBox.html" ,method=RequestMethod.GET)
	public ModelMap getCourseComboBox(CourseComboBoxDomain domain,HttpServletRequest request){
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("optionAll", domain.getOptionAll());
		if(request.isUserInRole(RoleDescription.Property.ADMIN)){
			modelMap.addAttribute("courseData", domain.getCourseComboBoxAdmin());
		}else{
			
		}
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/questionGroupComboBox.html" ,method=RequestMethod.POST)
	public ModelMap getQuestionGroupComboBox(QuestionGroupComboBoxDomain domain,HttpServletRequest request){
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("optionAll", domain.getOptionAll());
		if(request.isUserInRole(RoleDescription.Property.ADMIN)){
			modelMap.addAttribute("questionGroupData", domain.getQuestionGroupComboBoxAdmin());
		}else{
			
		}
		return modelMap;
	}
}