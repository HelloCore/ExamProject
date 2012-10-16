package th.ac.kbu.cs.ExamProject.Util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Aspect
public class LoggerAspect {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);
	
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object logWebRequest(ProceedingJoinPoint pjp) throws Throwable {
		
		Object obj;
        Long startTime = System.currentTimeMillis();
        
        WebAuthenticationDetails webAuthen = (WebAuthenticationDetails)SecurityUtils.getAuthentication().getDetails();
        
        StringBuilder sb = new StringBuilder("");
        sb.append("IP [").append(webAuthen.getRemoteAddress()).append("] SessionId [")
        	.append(webAuthen.getSessionId()).append("] UserType [");
        
        if(SecurityUtils.getAuthentication() instanceof AnonymousAuthenticationToken){
        	sb.append("AnonymousUser").append("]");
        }else {
        	sb.append(SecurityUtils.getTypeDesc())
        		.append("] Username [")
        		.append(SecurityUtils.getUsername())
        		.append("]");
        }     
        
        sb.append(" Class [").append(pjp.getSignature().getDeclaringTypeName()).append("]")
        .append(" Method [").append(pjp.getSignature().getName()).append("]");

        obj=pjp.proceed();
        
        sb.append(" Total Time [").append((System.currentTimeMillis() - startTime)).append(" ms]");
        
        LOGGER.info(sb.toString());
        
		return obj;
	}
}
