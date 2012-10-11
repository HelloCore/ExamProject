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

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.AddQuestionDomain;
import th.ac.kbu.cs.ExamProject.Domain.QuestionCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.QuestionDomain;

@Controller
public class QuestionManagementController {

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question/add.html" ,method=RequestMethod.GET)
	public ModelMap initAddQuestion(HttpServletRequest request){
		return new ModelMap();
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question/add.html" ,method=RequestMethod.POST)
	public void saveQuestion(@ModelAttribute AddQuestionDomain domain,HttpServletRequest request,HttpServletResponse reponse){
		domain.saveQuestion();
	}
	
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question.html" ,method=RequestMethod.GET)
	public ModelMap initQuestion(HttpServletRequest request){
		return new ModelMap();
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question.html" ,method=RequestMethod.POST,params={"method=getQuestionTable"})
	public @ResponseBody CoreGrid<HashMap<String,Object>> search(@ModelAttribute QuestionDomain domain,@ModelAttribute QuestionCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		return domain.search(gridManager);	
	}
	
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/question.html" ,method=RequestMethod.POST,params={"method=deleteQuestion"})
	public void delete(@ModelAttribute QuestionDomain domain,@ModelAttribute QuestionCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		domain.delete(gridManager);
	}


//	@ExceptionHandler(RuntimeException.class)
//	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
//	public @ResponseBody String runtimeException(RuntimeException ex,HttpServletRequest request,HttpServletResponse response){
//		return ex.getMessage();
//	}
}
