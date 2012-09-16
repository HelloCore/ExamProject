package th.ac.kbu.cs.ExamProject.Controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.CreateExamDomainTemp;
import th.ac.kbu.cs.ExamProject.Domain.DoExamDomain;
import th.ac.kbu.cs.ExamProject.Domain.ExamReportCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.ResultExamDomain;
import th.ac.kbu.cs.ExamProject.Domain.SaveExamDomain;
import th.ac.kbu.cs.ExamProject.Domain.SelectExamDomain;
import th.ac.kbu.cs.ExamProject.Exception.ExamException;

@Controller
public class ExamController {
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/selectExam.html" ,method=RequestMethod.GET)
	public ModelMap initSelectExam(@ModelAttribute SelectExamDomain domain,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		modelMap.addAttribute("examData", domain.getExam());
		modelMap.addAttribute("examResult", domain.getExamResult());
		return new ModelMap();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/createExam.html" ,method=RequestMethod.POST,params={"method=createExamResult"})
	public @ResponseBody Long createExamResult(@ModelAttribute CreateExamDomainTemp domain,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		return domain.createExamResultHql();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/doExam.html" ,method=RequestMethod.GET)
	public String redirect(@ModelAttribute DoExamDomain domain,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return "redirect:/exam/selectExam.html";
	}
		
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/doExam.html" ,method=RequestMethod.POST,params={"method=doExam"})
	public ModelMap selectExamResult(@ModelAttribute DoExamDomain domain,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		modelMap.addAttribute("questionAnswerData", domain.doExam());
		modelMap.addAttribute("expireDate",domain.getExpireDate().getTime());
		modelMap.addAttribute("numOfQuestion", domain.getNumOfQuestion());
		modelMap.addAttribute("examResultId", domain.getExamResultId());
		return new ModelMap();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/autoSaveExam.html" ,method=RequestMethod.POST,params={"method=autoSave"})
	public void autoSaveExam(@ModelAttribute SaveExamDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.autoSave();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/sendExam.html" ,method=RequestMethod.POST,params={"method=sendExam"})
	public void sendExam(@ModelAttribute SaveExamDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.sendExam();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/finishExam.html" ,method=RequestMethod.POST,params={"method=finishExam"})
	public @ResponseBody Boolean finishExamFromSelect(@ModelAttribute SaveExamDomain domain,HttpServletRequest request){
		return domain.finishExamFromSelect();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/validateExam.html" ,method=RequestMethod.POST,params={"method=validateExamResult"})
	public void validateExamResult(@ModelAttribute DoExamDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.validateExamResult();
	}

	@PreAuthorize(RoleDescription.hasAnyRole.WITHBOTH)
	@RequestMapping(value="/exam/viewResult.html" ,method=RequestMethod.POST,params={"method=viewResult"})
	public ModelMap resultExam(@ModelAttribute ResultExamDomain domain,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		if(request.isUserInRole(RoleDescription.Property.ADMIN) || request.isUserInRole(RoleDescription.Property.TEACHER)){
			modelMap.addAttribute("examResultData", domain.getExamResultData(false));
			modelMap.addAttribute("examResultAnswer", domain.getResultData());
		}else{
			modelMap.addAttribute("examResultData", domain.getExamResultData(true));
			// if isview
				// update is view
			//else
			modelMap.addAttribute("examResultAnswer", domain.getResultData());
			//end if
		}
		return modelMap;
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.WITHBOTH)
	@RequestMapping(value="/exam/examReport.html" ,method=RequestMethod.GET)
	public ModelMap examReport(@ModelAttribute ResultExamDomain domain,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return modelMap;
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.WITHBOTH)
	@RequestMapping(value="/exam/examReport.html" ,method=RequestMethod.POST,params={"method=getExamReport"})
	public @ResponseBody CoreGrid<HashMap<String,Object>> examReport(@ModelAttribute ResultExamDomain domain,@ModelAttribute ExamReportCoreGridManager gridManager,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return domain.searchStudent(gridManager);
	}
	
	@ExceptionHandler(ExamException.class)
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	public @ResponseBody String examException(ExamException ex,HttpServletRequest request,HttpServletResponse response){
		return ex.getMessage();
	}
	//การส่งข้อสอบ เพิ่มdeadline ให้ 5 นาทีเผื่อระบบdelay
	//ตอนเซฟ เช็คข้อมูลให้เรียบร้อย
	//ExamResultAnswerId ตรง
	//ExamResultId ตรง
}