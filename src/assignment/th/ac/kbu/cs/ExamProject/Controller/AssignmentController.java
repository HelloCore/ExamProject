package th.ac.kbu.cs.ExamProject.Controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.AssignmentDomain;

@Controller
public class AssignmentController {
	
	@RequestMapping(value="/assignment/select.html")
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	public ModelAndView init(ModelAndView mv,AssignmentDomain domain){
		mv.addObject("assignmentData", domain.getAssignmentData());
		return mv;
	}
	
	@RequestMapping(value="/assignment/select.html",method=RequestMethod.POST,params={"method=getAssignmentDetail"})
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	public @ResponseBody HashMap<String,Object> getAssignmentDetail(@ModelAttribute AssignmentDomain domain,HttpServletRequest request,HttpServletResponse response){
		return domain.getAssignmentDetail();
	}
	
}
