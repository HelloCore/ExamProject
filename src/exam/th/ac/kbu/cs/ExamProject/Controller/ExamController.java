package th.ac.kbu.cs.ExamProject.Controller;

import java.text.ParseException;

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

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.CreateExamDomain;
import th.ac.kbu.cs.ExamProject.Domain.DoExamDomain;
import th.ac.kbu.cs.ExamProject.Domain.SaveExamDomain;
import th.ac.kbu.cs.ExamProject.Domain.SelectExamDomain;
import th.ac.kbu.cs.ExamProject.Exception.ExamException;

@Controller
public class ExamController {

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/selectExam.html" ,method=RequestMethod.GET)
	public ModelMap initSelectExam(@ModelAttribute SelectExamDomain domain,ModelMap modelMap,HttpServletRequest request){
		modelMap.addAttribute("examData", domain.getExam());
		modelMap.addAttribute("examResult", domain.getExamResult());
		return new ModelMap();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/doExam.html" ,method=RequestMethod.POST,params={"method=createExamResult"})
	public @ResponseBody Long createExamResult(@ModelAttribute CreateExamDomain domain,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return domain.createExamResult();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/doExam.html" ,method=RequestMethod.POST,params={"method=doExam"})
	public ModelMap selectExamResult(@ModelAttribute DoExamDomain domain,ModelMap modelMap,HttpServletRequest request){
		modelMap.addAttribute("questionAnswerData", domain.doExam());
		modelMap.addAttribute("expireDate",domain.getExpireDate().getTime());
		modelMap.addAttribute("numOfQuestion", domain.getNumOfQuestion());
		modelMap.addAttribute("examResultId", domain.getExamResultId());
		return new ModelMap();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/doExam.html" ,method=RequestMethod.POST,params={"method=autoSave"})
	public void autoSaveExam(@ModelAttribute DoExamDomain domain,ModelMap modelMap,HttpServletRequest request){
		domain.autoSave();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/doExam.html" ,method=RequestMethod.POST,params={"method=sendExam"})
	public void sendExam(@ModelAttribute SaveExamDomain domain,ModelMap modelMap,HttpServletRequest request){
		domain.sendExam();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/selectExam.html" ,method=RequestMethod.POST,params={"method=sendExam"})
	public @ResponseBody Boolean sendExamFromSelect(@ModelAttribute SaveExamDomain domain,ModelMap modelMap,HttpServletRequest request){
		return domain.sendExamFromSelect();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHSTUDENT)
	@RequestMapping(value="/exam/selectExam.html" ,method=RequestMethod.POST,params={"method=validateExamResult"})
	public void validateExamResult(@ModelAttribute DoExamDomain domain,ModelMap modelMap,HttpServletRequest request){
		domain.validateExamResult();
	}
	
	@ExceptionHandler(ExamException.class)
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	public @ResponseBody String examException(ExamException ex,HttpServletRequest request){
		return ex.getMessage();
	}
	//การส่งข้อสอบ เพิ่มdeadline ให้ 5 นาทีเผื่อระบบdelay
	//ตอนเซฟ เช็คข้อมูลให้เรียบร้อย
	//ExamResultAnswerId ตรง
	//ExamResultId ตรง
}
