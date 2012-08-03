package th.ac.kbu.cs.ExamProject.Security.Service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Security.Domain.UserDomain;


@Component
public class UserDetailsService implements
			org.springframework.security.core.userdetails.UserDetailsService {

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDomain domain = new UserDomain();
		User user = domain.loadUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid Username or Password");
		}
		return new UserDetails(user);
	}
}

