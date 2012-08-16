package th.ac.kbu.cs.ExamProject.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LoggerListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import th.ac.kbu.cs.ExamProject.Security.Service.UserDetails;

public class SecurityLoggerListener extends LoggerListener{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SecurityLoggerListener.class);
	
	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent event){
		if(event instanceof AuthenticationSuccessEvent){
			handleSuccessEvent((AuthenticationSuccessEvent) event);
		}
	}
	
	private void handleSuccessEvent(AuthenticationSuccessEvent event){
		UserDetails userDetails = (UserDetails)event.getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		WebAuthenticationDetails authenDetail = (WebAuthenticationDetails)event.getAuthentication().getDetails();
		StringBuilder sb = new StringBuilder("");
		sb.append(" User Login >> Username [ ").append(username).append(" ] ").append(" ip address [ ")
			.append(authenDetail.getRemoteAddress()).append(" ] ").append(" session id [ ")
			.append(authenDetail.getSessionId()).append(" ] ");
		LOGGER.info(sb.toString());
	}
}
