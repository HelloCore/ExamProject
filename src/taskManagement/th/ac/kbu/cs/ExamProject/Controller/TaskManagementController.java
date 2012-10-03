package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.TaskDomain;

@Controller
public class TaskManagementController {
	
	@RequestMapping(value="/management/task.html")
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView init(ModelAndView mv){
		return mv;
	}
	
	@RequestMapping(value="/management/task/assign.html")
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView initAssign(ModelAndView mv){
		return mv;
	}
	
	@RequestMapping(value="/management/task/assign.html",method=RequestMethod.POST,params={"method=assignTask"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public void assignTask(@ModelAttribute TaskDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.assignTask();
	}
}
