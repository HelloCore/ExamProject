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
import org.springframework.web.servlet.ModelAndView;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.CourseCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.CourseDomain;

@Controller
public class CourseManagementController {
	
	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/management/course.html")
	public ModelMap init(ModelMap modelMap){
		return modelMap;
	}
	
	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/management/course.html" ,method=RequestMethod.POST,params={"method=getCourseTable"})
	public @ResponseBody CoreGrid<HashMap<String,Object>> search(@ModelAttribute CourseDomain domain,@ModelAttribute CourseCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		return domain.search(gridManager);	
	}
	
	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/management/course/save.html" ,method=RequestMethod.POST)
	public void save(@ModelAttribute CourseDomain domain,@ModelAttribute CourseCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		domain.save(gridManager);
	}
	
	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/management/course/delete.html" ,method=RequestMethod.POST)
	public void delete(@ModelAttribute CourseDomain domain,@ModelAttribute CourseCoreGridManager gridManager,HttpServletResponse reponse,HttpServletRequest request) {
		domain.delete(gridManager);
	}
	
	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/management/course/view.html",method=RequestMethod.POST,params={"method=viewCourseDetail"})
	public ModelAndView initViewDetail(@ModelAttribute CourseDomain domain,ModelAndView mv){
		mv.addObject("courseData", domain.getCourse());
		return mv;
	}
	
	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/management/course/view.html",method=RequestMethod.POST,params={"method=getTeacherTable"})
	public @ResponseBody List<HashMap<String,Object>> searchTeacher(@ModelAttribute CourseDomain domain,HttpServletResponse reponse,HttpServletRequest request) {
		return domain.searchTeacher();	
	}
	
	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/management/course/view/save.html" ,method=RequestMethod.POST)
	public void saveTeacher(@ModelAttribute CourseDomain domain,HttpServletResponse reponse,HttpServletRequest request) {
		domain.saveTeacher();
	}
	
	@PreAuthorize(RoleDescription.hasRole.ADMIN)
	@RequestMapping(value="/management/course/view/delete.html" ,method=RequestMethod.POST)
	public void deleteTeacher(@ModelAttribute CourseDomain domain,HttpServletResponse reponse,HttpServletRequest request) {
		domain.deleteTeacher();
	}
	
	
	
}
