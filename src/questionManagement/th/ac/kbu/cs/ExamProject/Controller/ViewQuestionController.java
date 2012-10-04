package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.ViewQuestionDomain;
import th.ac.kbu.cs.ExamProject.Entity.Answer;

@Controller
public class ViewQuestionController {

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question/view.html" ,method=RequestMethod.POST,params="method=viewQuestion")
	public ModelMap viewQuestion(@ModelAttribute ViewQuestionDomain domain,ModelMap modelMap,HttpServletRequest request){
		modelMap.addAttribute("questionData", domain.getQuestionData());
		modelMap.addAttribute("answerData", domain.getAnswerData());
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question/view.html" ,method=RequestMethod.POST,params="method=editQuestionParent")
	public void editQuestionParent(@ModelAttribute ViewQuestionDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.editQuestionParent();
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question/view.html" ,method=RequestMethod.POST,params="method=editQuestionText")
	public void editQuestionText(@ModelAttribute ViewQuestionDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.editQuestionText();
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question/view.html" ,method=RequestMethod.POST,params="method=deleteAnswer")
	public void deleteAnswer(@ModelAttribute ViewQuestionDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.deleteAnswer();
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question/view.html" ,method=RequestMethod.POST,params="method=editAnswer")
	public void editAnswer(@ModelAttribute ViewQuestionDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.editAnswer();
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question/view.html" ,method=RequestMethod.POST,params="method=addAnswer")
	public @ResponseBody Answer addAnswer(@ModelAttribute ViewQuestionDomain domain,HttpServletRequest request,HttpServletResponse response) throws Exception{
		return domain.addAnswer();
	}
	

}
