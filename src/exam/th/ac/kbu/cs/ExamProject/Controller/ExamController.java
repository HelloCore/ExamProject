package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.DoExamDomain;
import th.ac.kbu.cs.ExamProject.Exception.HibernateErrorException;

@Controller
public class ExamController {

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/selectExam.html" ,method=RequestMethod.GET)
	public ModelMap initSelectExam(DoExamDomain domain,HttpServletRequest request){
		return new ModelMap();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/doExam.html" ,method=RequestMethod.GET)
	public ModelMap doSelectExam(@ModelAttribute DoExamDomain domain,HttpServletRequest request){
		domain.createQuestion();
		return new ModelMap();
	}
	
	
	
}
