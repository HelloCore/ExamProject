package th.ac.kbu.cs.ExamProject.Controller;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.RegisterCoreGridManagement;
import th.ac.kbu.cs.ExamProject.Domain.RegisterManagementDomain;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Controller
public class RegisterManagementController {

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/register.html" ,method=RequestMethod.GET)
	public ModelMap init(ModelMap modelMap,HttpServletRequest request){
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/register.html" ,method=RequestMethod.POST,params={"method=getRegisterTable"})
	public @ResponseBody List<HashMap<String,Object>> getRegisterTable(@ModelAttribute RegisterManagementDomain domain ,HttpServletRequest request,HttpServletResponse response){
		return domain.getRegisterTable();
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/register.html" ,method=RequestMethod.POST,params={"method=acceptSection"})
	public void acceptSection(@ModelAttribute RegisterManagementDomain domain ,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
		domain.acceptSection(SecurityUtils.getUsername());
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/register.html" ,method=RequestMethod.POST,params={"method=rejectSection"})
	public void rejectSection(@ModelAttribute RegisterManagementDomain domain ,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
			domain.rejectSection(SecurityUtils.getUsername());
	}
	

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/register/history.html" ,method=RequestMethod.GET)
	public ModelMap initHistory(ModelMap modelMap,HttpServletRequest request){
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasRole.TEACHER)
	@RequestMapping(value="/management/register/history.html" ,method=RequestMethod.POST,params={"method=getHistoryTable"})
	public @ResponseBody CoreGrid<HashMap<String,Object>> viewHistory(@ModelAttribute RegisterManagementDomain domain ,@ModelAttribute RegisterCoreGridManagement gridManager,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
		return domain.search(gridManager);
	}

	
}
