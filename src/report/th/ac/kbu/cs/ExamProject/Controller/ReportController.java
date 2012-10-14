package th.ac.kbu.cs.ExamProject.Controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.ExamReportDomain;
import th.ac.kbu.cs.ExamProject.Domain.ExamResultReportCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.ResultExamDomain;
import th.ac.kbu.cs.ExamProject.Domain.StudentReportCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.StudentReportDomain;
import th.ac.kbu.cs.ExamProject.Domain.TaskDomain;

@Controller
public class ReportController {

	@RequestMapping(value="/report/exam.html")
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView initExamReport(ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		return mv;
	}
	
	@RequestMapping(value="/report/examDetail.html",method=RequestMethod.POST,params={"method=viewExamReportDetail"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView viewExamReportDetail(@ModelAttribute ExamReportDomain domain,ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		mv.addObject("examDetail", domain.getExamDetail());
		return mv;
	}
	
	@RequestMapping(value="/report/examDetail.html",method=RequestMethod.POST,params={"method=getExamDetailTable"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public @ResponseBody CoreGrid<HashMap<String,Object>> getExamDetailTable(@ModelAttribute ExamReportDomain domain,@ModelAttribute ExamResultReportCoreGridManager gridManager,HttpServletRequest request,HttpServletResponse response){
		return domain.getExamDetailTable(gridManager);
	}
	
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/report/examResult.html" ,method=RequestMethod.POST,params={"method=viewResult"})
	public ModelMap resultExam(@ModelAttribute ResultExamDomain domain,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		modelMap.addAttribute("examResultData", domain.getExamResultData(false));
		modelMap.addAttribute("examResultAnswer", domain.getResultData(false));
		return modelMap;
	}
	

	@RequestMapping(value="/report/assignment.html")
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView initAssignmentReport(ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		return mv;
	}
	

	@RequestMapping(value="/report/assignmentResult.html")
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView initAssignmentResult(@ModelAttribute TaskDomain domain,ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		mv.addObject("taskData", domain.getAndValidateTaskData());
		mv.addObject("evaluatedList",domain.getEvaluatedList(TaskDomain.SORT_BY_STUDENT_ID));
		return mv;
	}
	
	@RequestMapping(value="/report/student.html")
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView initStudentReport(ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		return mv;
	}
	
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/report/student.html" ,method=RequestMethod.POST,params={"method=getStudentTable"})
	public @ResponseBody CoreGrid<HashMap<String,Object>> search(@ModelAttribute StudentReportDomain domain,@ModelAttribute StudentReportCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		return domain.search(gridManager);	
	}
	

	@RequestMapping(value="/report/studentDetail.html",method=RequestMethod.POST,params={"method=viewStudentDetail"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView initStudentDetail(@ModelAttribute StudentReportDomain domain,ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		mv.addObject("studentData", domain.getStudentData());
		mv.addObject("examData",domain.getExamData());
		mv.addObject("evaluatedList",domain.getEvaluatedList());
		return mv;
	}
	
}
