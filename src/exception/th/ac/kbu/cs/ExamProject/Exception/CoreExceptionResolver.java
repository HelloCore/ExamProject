package th.ac.kbu.cs.ExamProject.Exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CoreExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = null;
		
		if(ex instanceof AccessDeniedException){
			mv = new ModelAndView("redirect:/errors/access-denied.html");
		}else if (ex instanceof ContentFileException){
			ContentFileException cfe = (ContentFileException) ex;
			mv = new ModelAndView("redirect:/main/content.html?path="+cfe.getFolderId()+"&error="+cfe.getMessage());
		}else if (ex instanceof HibernateException){
			mv = new ModelAndView("errorJson");
			mv.addObject("message", ex.getMessage());
		}else if (ex instanceof MainException){
			mv = new ModelAndView("errorJson");
			mv.addObject("message", ex.getMessage());
		}else if (ex instanceof NullPointerException){
			mv = new ModelAndView("errorJson");
			mv.addObject("message", ex.getMessage());
		}
		response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		return mv;
	}

}
