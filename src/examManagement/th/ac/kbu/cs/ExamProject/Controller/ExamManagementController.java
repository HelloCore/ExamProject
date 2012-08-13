package th.ac.kbu.cs.ExamProject.Controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.AddExamDomain;

@Controller
public class ExamManagementController {
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/exam/add.html" ,method=RequestMethod.GET)
	public ModelMap init(HttpServletRequest request,ModelMap modelMap){
		return modelMap;
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/exam/add.html" ,method=RequestMethod.POST,params="method=addExam")
	public void addExam(@ModelAttribute AddExamDomain domain,HttpServletRequest request,ModelMap modelMap) throws ParseException{
		System.out.println("Controller");
		domain.addExam();
	}
}
