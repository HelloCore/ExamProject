package th.ac.kbu.cs.ExamProject.Controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.NewsCoreGridManager;
import th.ac.kbu.cs.ExamProject.Domain.NewsDomain;
import th.ac.kbu.cs.ExamProject.Domain.ViewNewsDomain;

@Controller
public class NewsManagementController {

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/news.html")
	public ModelMap init(ModelMap modelMap){
		return modelMap;
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/news.html",method=RequestMethod.POST,params={"method=getNewsTable"})
	public @ResponseBody CoreGrid<HashMap<String,Object>> search(@ModelAttribute NewsDomain domain,@ModelAttribute NewsCoreGridManager gridManager,HttpServletRequest request,HttpServletResponse response){
		return domain.search(gridManager);
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/news.html",method=RequestMethod.POST,params={"method=deleteNews"})
	public void deleteNews(@ModelAttribute NewsDomain domain,@ModelAttribute NewsCoreGridManager gridManager,HttpServletRequest request,HttpServletResponse response){
		domain.deleteNews(gridManager);
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/news/add.html")
	public ModelMap initAdd(ModelMap modelMap){
		return modelMap;
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/news/add.html",method=RequestMethod.POST,params={"method=addNews"})
	public void addNews(@ModelAttribute NewsDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.addNews();
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/news/view.html",method=RequestMethod.POST)
	public ModelMap initView(@RequestParam(required=true,value="newsId") Long newsId,ViewNewsDomain domain,ModelMap modelMap){
		modelMap.addAttribute("courseData", domain.getCourseData());
		modelMap.addAttribute("newsData", domain.getNews(newsId));
		return modelMap;
	}

	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/management/news/view.html",method=RequestMethod.POST,params={"method=editNews"})
	public void editNews(@ModelAttribute ViewNewsDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.editNews();
	}
	
	
}
