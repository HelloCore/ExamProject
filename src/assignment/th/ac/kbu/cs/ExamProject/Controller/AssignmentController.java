package th.ac.kbu.cs.ExamProject.Controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.AssignmentDomain;
import th.ac.kbu.cs.ExamProject.Domain.AssignmentResultCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.SubmitAssignmentDomain;

@Controller
public class AssignmentController {
	
	@RequestMapping(value="/assignment/select.html")
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	public ModelAndView init(@RequestParam(value="error",required=false) String error,ModelAndView mv,AssignmentDomain domain){
		mv.addObject("assignmentData", domain.getAssignmentData());
		mv.addObject("error",error);
		return mv;
	}
	
	@RequestMapping(value="/assignment/select.html",method=RequestMethod.POST,params={"method=getAssignmentDetail"})
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	public @ResponseBody HashMap<String,Object> getAssignmentDetail(@ModelAttribute AssignmentDomain domain,HttpServletRequest request,HttpServletResponse response){
		return domain.getAssignmentDetail();
	}

	@RequestMapping(value="/assignment/submit.html",method=RequestMethod.POST,params={"method=submitAssignment"})
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	public ModelAndView submitAssignment(@ModelAttribute AssignmentDomain domain,ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		mv.addObject("assignmentData",domain.getAssignmentDetail());
		return mv;
	}
	
	@RequestMapping(value="/assignment/submitFile.html",method=RequestMethod.POST)
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	public ModelAndView submitFile(@ModelAttribute SubmitAssignmentDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.submitAssignment();
		return new ModelAndView("redirect:/assignment/result.html?success=Submit Complete");
	}
	
	@RequestMapping(value="/assignment/result.html")
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	public ModelAndView initResult(@RequestParam(value="success",required=false) String success,ModelAndView mv,AssignmentDomain domain){
		mv.addObject("success", success);
		return mv;
	}
	
	@RequestMapping(value="/assignment/result.html",method=RequestMethod.POST,params={"method=getAssignmentResultTable"})
	@PreAuthorize(RoleDescription.hasRole.STUDENT)
	public @ResponseBody CoreGrid<HashMap<String,Object>> getAssignmentResult(@ModelAttribute AssignmentDomain domain,@ModelAttribute AssignmentResultCoreGridManager gridManager,HttpServletRequest request,HttpServletResponse response){
		return domain.getAssignmentResult(gridManager);
	}
	
}
