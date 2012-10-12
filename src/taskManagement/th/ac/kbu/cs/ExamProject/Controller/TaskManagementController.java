package th.ac.kbu.cs.ExamProject.Controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

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
import th.ac.kbu.cs.ExamProject.Description.PathDescription;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.TaskCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.TaskDomain;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentFile;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Exception.CoreRedirectException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@RequestMapping(value="/management/task.html",method=RequestMethod.POST,params={"method=evaluateComplete"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public void evaluateComplete(@ModelAttribute TaskDomain domain,@ModelAttribute TaskCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request){
		domain.evaluateComplete(gridManager);
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
	
	@RequestMapping(value="/management/task/taskList.html")
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView initTaskList(@RequestParam(value="error",required=false) String error,@ModelAttribute TaskDomain domain,ModelAndView mv){
		mv.addObject("error", error);
		return mv;
	}
	
	@RequestMapping(value="/management/task/taskList.html",method=RequestMethod.POST,params={"method=getTaskList"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public @ResponseBody List<Object[]> getTaskList(@ModelAttribute TaskDomain domain,HttpServletRequest request,HttpServletResponse response){
		return domain.getTaskList();
	}
	
	@RequestMapping(value="/management/task/sendList.html",method=RequestMethod.POST,params={"method=initSendList"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView initSendList(@ModelAttribute TaskDomain domain,ModelAndView mv){
		mv.addObject("taskData", domain.getAndValidateTaskData());
		mv.addObject("sendList",domain.getSendList());
		mv.addObject("evaluatedList",domain.getEvaluatedList());
		return mv;
	}
	
	@RequestMapping(value="/management/task/evaluate.html",method=RequestMethod.POST,params={"method=evaluateWork"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView initEvaluate(@ModelAttribute TaskDomain domain,ModelAndView mv){
		mv.addObject("workData", domain.getAndValidateWorkData());
		mv.addObject("fileList",domain.getFileList());
		return mv;
	}
	
	@RequestMapping(value="/management/task/evaluate.html",method=RequestMethod.POST,params={"method=evaluate"})
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView evaluate(@ModelAttribute TaskDomain domain,ModelAndView mv){
		domain.evaluateWork();
		
		mv.setViewName("management/task/sendList");
		mv.addObject("taskData", domain.getAndValidateTaskData());
		mv.addObject("sendList",domain.getSendList());
		mv.addObject("evaluatedList",domain.getEvaluatedList());
		mv.addObject("success", true);
		return mv;
	}
	
	@RequestMapping(value="/management/task/getFile.html")
	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	public ModelAndView getFile(@ModelAttribute TaskDomain domain,HttpServletRequest request,HttpServletResponse response){
		AssignmentFile assignmentFile = domain.getAndValidateFile();
		
		response.setContentType(assignmentFile.getContentType());
		response.setHeader("Content-Disposition", "attachment;filename="+assignmentFile.getFileName());
		try {
			OutputStream out = response.getOutputStream();
			out.write(assignmentFile.getContent());
			out.flush();
		} catch (IOException e) {
			throw new CoreRedirectException(new CoreExceptionMessage(e.getMessage()), PathDescription.Management.Task.TASKLIST);
		}
		
		return null;
	}
	
}
