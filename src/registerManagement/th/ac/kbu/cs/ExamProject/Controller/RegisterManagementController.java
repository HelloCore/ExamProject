package th.ac.kbu.cs.ExamProject.Controller;

import java.util.HashMap;
import java.util.List;

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
import th.ac.kbu.cs.ExamProject.Domain.RegisterManagementDomain;

@Controller
public class RegisterManagementController {

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/register.html" ,method=RequestMethod.GET)
	public ModelMap init(ModelMap modelMap,HttpServletRequest request){
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/register.html" ,method=RequestMethod.POST,params={"method=getRegisterTable"})
	public @ResponseBody List<HashMap<String,Object>> getRegisterTable(@ModelAttribute RegisterManagementDomain domain ,HttpServletRequest request,HttpServletResponse response){
		return domain.getRegisterTable();
	}
}
