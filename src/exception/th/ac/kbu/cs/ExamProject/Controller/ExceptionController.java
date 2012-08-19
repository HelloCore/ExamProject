package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import th.ac.kbu.cs.ExamProject.Exception.HibernateErrorException;
//@Controller
public class ExceptionController {

//	@ExceptionHandler(HibernateErrorException.class)
//	public @ResponseBody String hibernateErrorException(HibernateErrorException ex,HttpServletRequest request){
//		System.out.println("HibernateErrorException");
//		return "HibernateErrorException";
//	}
}
