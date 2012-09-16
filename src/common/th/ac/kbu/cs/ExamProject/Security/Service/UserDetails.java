package th.ac.kbu.cs.ExamProject.Security.Service;

import th.ac.kbu.cs.ExamProject.Entity.User;


public class UserDetails extends org.springframework.security.core.userdetails.User  {

	private static final long serialVersionUID = 6914021153601941485L;

	private User user;

	public UserDetails(User user) {
		super(user.getUsername(), user.getPassword(), user
				.getGrantedAuthorities());
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
