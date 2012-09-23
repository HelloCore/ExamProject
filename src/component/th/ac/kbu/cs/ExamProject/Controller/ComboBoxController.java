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
import th.ac.kbu.cs.ExamProject.Domain.RegisterCourseComboBox;
import th.ac.kbu.cs.ExamProject.Domain.RegisterSectionComboBox;
import th.ac.kbu.cs.ExamProject.Domain.SectionComboBoxDomain;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

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
			modelMap.addAttribute("courseData",domain.getCourseComboBoxTeacher(SecurityUtils.getUsername()));
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
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	@RequestMapping(value="/member/registerCourseComboBox.html" ,method=RequestMethod.GET)
	public ModelMap getRegisterCourseComboBox(@ModelAttribute RegisterCourseComboBox domain,ModelMap modelMap,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		modelMap.addAttribute("courseData",domain.getCourseData(SecurityUtils.getUsername()));
		return modelMap;
	}
	
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	@RequestMapping(value="/member/registerCourseSectionComboBox.html" ,method=RequestMethod.GET)
	public @ResponseBody List<Object[]> getRegisterCourseComboBox(@ModelAttribute RegisterSectionComboBox domain,ModelMap modelMap,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		return domain.getCourseSectionData(SecurityUtils.getUsername());
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.WITHBOTH)
	@RequestMapping(value="/member/registerSectionComboBox.html" ,method=RequestMethod.POST,params={"method=register"})
	public @ResponseBody List<Object[]> getRegisterSectionComboBox(@ModelAttribute RegisterSectionComboBox domain,ModelMap modelMap,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		List<Object[]> result = null;
		if(request.isUserInRole(RoleDescription.Property.ADMIN)){
			result = domain.getSectionAdminData();
		}else if(request.isUserInRole(RoleDescription.Property.TEACHER)){
			result = domain.getSectionTeacherData(SecurityUtils.getUsername());
		}else{
			result =  domain.getSectionStudentData(SecurityUtils.getUsername());
		}
		return result;
	}
	
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	@RequestMapping(value="/member/registerSectionComboBox.html" ,method=RequestMethod.POST,params={"method=changeSection"})
	public @ResponseBody List<Object[]> getChangeSectionComboBox(@ModelAttribute RegisterSectionComboBox domain,ModelMap modelMap,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		return domain.getChangeSectionStudentData(SecurityUtils.getUsername());
	}
	
}