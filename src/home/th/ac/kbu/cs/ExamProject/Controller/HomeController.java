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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.ChangeUserDataDomain;
import th.ac.kbu.cs.ExamProject.Domain.ForgotPasswordDomain;
import th.ac.kbu.cs.ExamProject.Domain.ViewNewsDomain;

@Controller
public class HomeController {

	@RequestMapping(value={"/main/home.html","/home.html"})
	public ModelMap init(ModelMap modelMap,ViewNewsDomain domain,HttpServletRequest request){
		modelMap.addAttribute("newsData", domain.getNewsList(0,5));
		return modelMap;
	}

	@RequestMapping(value="/main/readMoreNews.html",method=RequestMethod.POST,params={"method=getNews"})
	public @ResponseBody List<HashMap<String,Object>> getNews(@RequestParam(required=true,value="start") Integer start
														,ViewNewsDomain domain,HttpServletRequest request){
		return domain.getNewsList(start,20);
	}
	
	@RequestMapping(value="/main/readMoreNews.html",method=RequestMethod.POST,params={"method=getNewsCount"})
	public @ResponseBody Long getNewsCount(ViewNewsDomain domain,HttpServletRequest request,HttpServletResponse response){
		return domain.getNewsCount();
	}
	
	@RequestMapping(value="/main/readMoreNews.html")
	public ModelMap readMoreNews(ModelMap modelMap,ViewNewsDomain domain,HttpServletRequest request){
		return modelMap;
	}
	
	@RequestMapping(value="/main/readNews.html")
	public ModelAndView readNews(@ModelAttribute ViewNewsDomain domain,ModelAndView mav,HttpServletRequest request,HttpServletResponse response){
		mav.addObject("newsInfo",domain.getNewsInfo());
		return mav;
	}

	@RequestMapping(value="/errors/access-denied.html",method=RequestMethod.GET)
	public ModelAndView accessDenied(ModelAndView mv){
		return mv;
	}

	
	@RequestMapping(value="/member/changePassword.html",method=RequestMethod.GET)
	@PreAuthorize(RoleDescription.HAS_ALL_ROLE)
	public ModelAndView initChangePassword(ModelAndView mv){
		return mv;
	}
	
	@RequestMapping(value="/member/changePassword.html",method=RequestMethod.POST)
	@PreAuthorize(RoleDescription.HAS_ALL_ROLE)
	public void changePassword(@ModelAttribute ChangeUserDataDomain domain,HttpServletRequest request,HttpServletResponse response){
		domain.changePassword();
	}

	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/forgotPassword.html",method=RequestMethod.GET)
	public ModelAndView initForgotPassword(@RequestParam(value="studentId",required=false)String studentId,ModelAndView mv){
		return mv;
	}

	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/forgotPassword.html",method=RequestMethod.POST)
	public void forgotPassword(@ModelAttribute ForgotPasswordDomain domain,HttpServletResponse respones,HttpServletRequest request){
		domain.forgotPassword();
	}
	
	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/forgotPasswordCode.html",method=RequestMethod.GET)
	public ModelAndView initForgotPasswordCode(@RequestParam(value="studentId",required=false)String studentId,ModelAndView mv){
		mv.addObject("studentId", studentId);
		return mv;
	}

	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/main/forgotPasswordCode.html",method=RequestMethod.POST)
	public void changePassword(@ModelAttribute ForgotPasswordDomain domain,HttpServletResponse respones,HttpServletRequest request){
		domain.changePassword();
	}
	
	
}

