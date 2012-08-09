package th.ac.kbu.cs.ExamProject.Util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAspect {
	private final static Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);
	@AfterThrowing(
		      pointcut = "execution(* th.ac.kbu.cs.ExamProject.*.*.*(..))",
		      throwing= "error")
	public void allThrowing(JoinPoint joinPoint, Throwable error){
		error.printStackTrace();
	}
	
//	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//	public Object logWebRequest(ProceedingJoinPoint pjp) throws Throwable {
//		Object obj;
//        Long startTime = System.currentTimeMillis();
//        if (SecurityContextHolder.getContext().getAuthentication() != null) {
//        	LOGGER.info(" User: ",SecurityUtils.getUsername());
//        }
//        LOGGER.info("Controller UUID: " + pjp. );
//        LOGGER.info("Controller URL: " + requestInfo.getRequestMethod());
//        LOGGER.info("Controller Method: " + pjp.getSignature().);
//        
//		return null;
//	}
	
//	@Around("execution(* th.ac.kbu.cs.ExamProject.Service.*.*(..))")
//	public Object logServiceProcess(ProceedingJoinPoint pjp) throws Throwable{
//		Long startTime = System.currentTimeMillis();
//		LOGGER.info("Start Method:["+pjp.getSignature().toString()+"] Params:"+pjp.getArgs().toString());
//		Object obj = pjp.proceed();
//		LOGGER.info("End Method:["+pjp.getSignature().toString()+"] Return: "+obj);
//		LOGGER.info("Total Time:["+(System.currentTimeMillis() - startTime) + " ms]");
//		return obj;
//	}
//	@AfterReturning(
//			pointcut = "execution(* th.ac.kbu.cs.ExamProject.Domain.*.*(..)) ",
//			returning ="result")
//	public void logDomainTest(JoinPoint joinPoint, Object result){
//		LOGGER.info("Start Domain:["+joinPoint.getSignature().toShortString()+"] Params: "+joinPoint.getArgs().toString());
//		LOGGER.info("End Domain:["+joinPoint.getSignature().toShortString()+"] Return: "+result);
//	}
//	@AfterReturning(
//	    pointcut = "execution(* th.ac.kbu.cs.ExamProject.Service.Impl.*.*(..))",
//	    returning= "result")
//	public void logServiceProcess(JoinPoint joinPoint, Object result) {
//		LOGGER.info("Start Service Method:["+joinPoint.getSignature().toShortString()+"] Params: "+joinPoint.getArgs().toString());
//		LOGGER.info("End Service Method:["+joinPoint.getSignature().toShortString()+"] Return: "+result);
//	}
}
