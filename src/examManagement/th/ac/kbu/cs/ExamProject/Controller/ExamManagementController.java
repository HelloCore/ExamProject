package th.ac.kbu.cs.ExamProject.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.AddExamDomain;
import th.ac.kbu.cs.ExamProject.Domain.ExamCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.ExamDomain;
import th.ac.kbu.cs.ExamProject.Domain.ViewExamDomain;
import th.ac.kbu.cs.ExamProject.Entity.Exam;
import th.ac.kbu.cs.ExamProject.Entity.ExamQuestionGroup;

@Controller
public class ExamManagementController {
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/exam/add.html" ,method=RequestMethod.GET)
	public ModelMap initAdd(HttpServletRequest request,ModelMap modelMap){
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/exam.html" ,method=RequestMethod.GET)
	public ModelMap initManagement(HttpServletRequest request,ModelMap modelMap){
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/exam/view.html" ,method=RequestMethod.GET,params="method=viewExam")
	public ModelMap initViewExam(@ModelAttribute ViewExamDomain domain,HttpServletRequest request,ModelMap modelMap) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		Exam exam = domain.getExamData();
		String questionGroupData = mapper.writeValueAsString(domain.getQuestionGroupData(exam.getCourseId()));
		String sectionData = mapper.writeValueAsString(domain.getSectionData(exam.getCourseId()));
		String examQuestionGroupData = mapper.writeValueAsString(domain.getExamQuestionGroupData());
		String examSectionData =  mapper.writeValueAsString(domain.getExamSectionData());
		modelMap.addAttribute("examData", exam);
		modelMap.addAttribute("questionGroupData", questionGroupData);
		modelMap.addAttribute("sectionData", sectionData);
		modelMap.addAttribute("examQuestionGroupData", examQuestionGroupData);
		modelMap.addAttribute("examSectionData", examSectionData);
		return modelMap;
	}
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/exam/view.html" ,method=RequestMethod.POST,params="method=editExamQuestionGroup")
	public @ResponseBody List<ExamQuestionGroup> editExamQuestionGroup(@ModelAttribute ViewExamDomain domain,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
		return domain.editExamQuestionGroup();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/exam/add.html" ,method=RequestMethod.POST,params="method=addExam")
	public void addExam(@ModelAttribute AddExamDomain domain,HttpServletRequest request,HttpServletResponse reponse) throws ParseException{
		domain.addExam();
	}
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/exam.html" ,method=RequestMethod.POST,params={"method=getExamTable"})
	public @ResponseBody CoreGrid<HashMap<String,Object>> search(@ModelAttribute ExamDomain domain,@ModelAttribute ExamCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		CoreGrid<HashMap<String,Object>> grid = null;
		if(request.isUserInRole(RoleDescription.Property.ADMIN)){
			grid = domain.searchAdmin(gridManager);
		}else{
			
		}
		return grid;	
	}

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/exam.html" ,method=RequestMethod.POST,params={"method=deleteExam"})
	public void delete(@ModelAttribute ExamDomain domain,@ModelAttribute ExamCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		domain.delete(gridManager);
	}
}
