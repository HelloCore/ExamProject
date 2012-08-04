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
import th.ac.kbu.cs.ExamProject.Domain.SectionCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.SectionDomain;

@Controller
public class SectionManagementController {

	@RequestMapping(value="/management/section.html")
	public ModelMap init(ModelMap modelMap){
		return modelMap;
	}
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/section.html" ,method=RequestMethod.POST,params={"method=getSectionTable"})
	public @ResponseBody CoreGrid<HashMap<String,Object>> search(@ModelAttribute SectionDomain domain,@ModelAttribute SectionCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		CoreGrid<HashMap<String,Object>> gridSection = null;
		if(request.isUserInRole(RoleDescription.Property.ADMIN)){
			gridSection = domain.searchAdmin(gridManager);
		}else{
			gridSection = domain.searchTeacher(gridManager);
		}
		return gridSection;	
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/section/save.html" ,method=RequestMethod.POST)
	public void save(@ModelAttribute SectionDomain domain,@ModelAttribute SectionCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		domain.save(gridManager);
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/section/delete.html" ,method=RequestMethod.POST)
	public void delete(@ModelAttribute SectionDomain domain,@ModelAttribute SectionCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		domain.delete(gridManager);
	}
}
