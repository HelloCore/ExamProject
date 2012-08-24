package th.ac.kbu.cs.ExamProject.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.CourseComboBoxDomain;
import th.ac.kbu.cs.ExamProject.Domain.QuestionGroupComboBoxDomain;
import th.ac.kbu.cs.ExamProject.Domain.SectionComboBoxDomain;

@Controller
public class ComboBoxController {
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/courseComboBox.html" ,method=RequestMethod.GET)
	public ModelMap getCourseComboBox(@ModelAttribute CourseComboBoxDomain domain,HttpServletRequest request){
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
	public ModelMap getQuestionGroupComboBox(@ModelAttribute QuestionGroupComboBoxDomain domain,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("optionAll", domain.getOptionAll());
		if(request.isUserInRole(RoleDescription.Property.ADMIN)){
			modelMap.addAttribute("questionGroupData", domain.getQuestionGroupComboBoxAdmin());
		}else{
			
		}
		return modelMap;
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/sectionComboBox.html" ,method=RequestMethod.POST)
	public @ResponseBody List<HashMap<String,Object>> getSectionComboBox(@ModelAttribute SectionComboBoxDomain domain,HttpServletRequest request){
		List<HashMap<String,Object>> sectionData = null;
		if(request.isUserInRole(RoleDescription.Property.ADMIN)){
			sectionData = domain.getSectionComboBoxAdmin();
		}else{
			
		}
		return sectionData;
	}
	
}