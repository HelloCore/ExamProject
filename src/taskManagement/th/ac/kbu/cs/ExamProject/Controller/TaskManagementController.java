package th.ac.kbu.cs.ExamProject.Controller;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.TaskCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.TaskDomain;

@Controller
public class TaskManagementController {
	
	@RequestMapping(value="/management/task.html")
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView init(ModelAndView mv){
		return mv;
	}
	
	@RequestMapping(value="/management/task.html",method=RequestMethod.POST,params={"method=getTaskTable"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public @ResponseBody CoreGrid<HashMap<String,Object>> search(@ModelAttribute TaskDomain domain,@ModelAttribute TaskCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		return domain.search(gridManager);
	}
	
	@RequestMapping(value="/management/task.html",method=RequestMethod.POST,params={"method=deleteTask"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public void deleteTask(@ModelAttribute TaskDomain domain,@ModelAttribute TaskCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request){
		domain.deleteTask(gridManager);
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
	
	@RequestMapping(value="/management/task/view.html",method=RequestMethod.POST,params={"method=viewTask"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView viewTask(@ModelAttribute TaskDomain domain,ModelAndView mv,HttpServletRequest request,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		mv.addObject("taskData", domain.getTaskData());
		mv.addObject("sectionData",mapper.writeValueAsString(domain.getSectionData()));
		return mv;
	}

	@RequestMapping(value="/management/task/view.html",method=RequestMethod.POST,params={"method=editTask"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public void editTask(@ModelAttribute TaskDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.editTask();
	}
}
