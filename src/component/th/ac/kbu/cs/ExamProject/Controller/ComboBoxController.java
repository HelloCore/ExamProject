package th.ac.kbu.cs.ExamProject.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import th.ac.kbu.cs.ExamProject.Bean.SectionComboBoxBean;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.CourseComboBoxDomain;
import th.ac.kbu.cs.ExamProject.Domain.QuestionGroupComboBoxDomain;
import th.ac.kbu.cs.ExamProject.Domain.RegisterCourseComboBox;
import th.ac.kbu.cs.ExamProject.Domain.RegisterSectionComboBox;
import th.ac.kbu.cs.ExamProject.Domain.SectionComboBoxDomain;
import th.ac.kbu.cs.ExamProject.Domain.TeacherComboBoxDomain;
import th.ac.kbu.cs.ExamProject.Entity.Section;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/sectionComboBox.html" ,method=RequestMethod.POST)
	public @ResponseBody List<HashMap<String,Object>> getSectionComboBox(@ModelAttribute SectionComboBoxDomain domain,HttpServletRequest request){
		return domain.getSectionComboBox();
	}
	
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/sectionComboBox.html" ,method=RequestMethod.POST,params={"group=1"})
	public @ResponseBody List<SectionComboBoxBean<Section>> getGroupSectionComboBox(@ModelAttribute SectionComboBoxDomain domain,HttpServletRequest request){
		List<HashMap<String,Object>> result = domain.getSectionComboBox();
		Map<String,SectionComboBoxBean<Section>> output = new HashMap<String, SectionComboBoxBean<Section>>();
		
		for(HashMap<String,Object> rawSection : result){
			String currentKey = rawSection.get("sectionSemester").toString()+rawSection.get("sectionYear").toString();

			Section section = new Section();
			section.setSectionId(BeanUtils.toLong(rawSection.get("sectionId")));
			section.setSectionName(BeanUtils.toString(rawSection.get("sectionName")));
			
			if(BeanUtils.isNull(output.get(currentKey))){
				SectionComboBoxBean<Section> groupSectionBean = new SectionComboBoxBean<Section>();
				groupSectionBean.setSectionSemester(BeanUtils.toInteger(rawSection.get("sectionSemester")));
				groupSectionBean.setSectionYear(BeanUtils.toInteger(rawSection.get("sectionYear")));
				groupSectionBean.addSection(section);
				
				output.put(currentKey, groupSectionBean);
			}else{
				SectionComboBoxBean<Section> groupSectionBean = (SectionComboBoxBean<Section>) output.get(currentKey);
				groupSectionBean.addSection(section);
				
			}
		}
		
		return new ArrayList<SectionComboBoxBean<Section>>(output.values());
	}
	

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/courseSectionComboBox.html" ,method=RequestMethod.GET)
	public @ResponseBody List<Object[]> getCourseSectionComboBox(@ModelAttribute RegisterSectionComboBox domain,ModelMap modelMap,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		return domain.getCourseSectionDataTeacher(SecurityUtils.getUsername());
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/questionGroupComboBox.html" ,method=RequestMethod.POST)
	public ModelMap getQuestionGroupComboBox(@ModelAttribute QuestionGroupComboBoxDomain domain,HttpServletRequest request,ModelMap modelMap) throws JsonParseException, JsonMappingException, IOException{
		modelMap.addAttribute("optionAll", domain.getOptionAll());
		modelMap.addAttribute("questionGroupData", domain.getQuestionGroupComboBox());
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
	public @ResponseBody List<SectionComboBoxBean<Object[]>> getRegisterSectionComboBox(@ModelAttribute RegisterSectionComboBox domain,ModelMap modelMap,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		List<Object[]> result = null;
		if(request.isUserInRole(RoleDescription.Property.ADMIN)){
			result = domain.getSectionAdminData();
		}else if(request.isUserInRole(RoleDescription.Property.TEACHER)){
			result = domain.getSectionTeacherData(SecurityUtils.getUsername());
		}else{
			result =  domain.getSectionStudentData(SecurityUtils.getUsername());
		}

		Map<String,SectionComboBoxBean<Object[]>> output = new HashMap<String, SectionComboBoxBean<Object[]>>();
		for(Object[] section: result){
			String currentKey = section[3].toString()+section[2].toString();
			if(BeanUtils.isNull(output.get(currentKey))){
				SectionComboBoxBean<Object[]> regBean = new SectionComboBoxBean<Object[]>();
				regBean.setSectionSemester(BeanUtils.toInteger(section[3]));
				regBean.setSectionYear(BeanUtils.toInteger(section[2]));
				regBean.addSection(section);
				
				output.put(currentKey, regBean);
			}else{
				SectionComboBoxBean<Object[]> regBean = (SectionComboBoxBean<Object[]>)output.get(currentKey);
				regBean.addSection(section);
			}
		}
		
		return new ArrayList<SectionComboBoxBean<Object[]>>(output.values());
	}
	
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	@RequestMapping(value="/member/registerSectionComboBox.html" ,method=RequestMethod.POST,params={"method=changeSection"})
	public @ResponseBody List<Object[]> getChangeSectionComboBox(@ModelAttribute RegisterSectionComboBox domain,ModelMap modelMap,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		return domain.getChangeSectionStudentData(SecurityUtils.getUsername());
	}

	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/management/teacherComboBox.html" ,method=RequestMethod.POST)
	public @ResponseBody List<HashMap<String,Object>> getTeacherComboBox(@ModelAttribute TeacherComboBoxDomain domain,HttpServletRequest request){
		return domain.getTeacherComboBox();
	}

	
}