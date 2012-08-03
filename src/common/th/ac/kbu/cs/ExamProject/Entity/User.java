package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Entity
@Table(name = "USERS")
public class User implements Serializable{
	
	private static final long serialVersionUID = 8310034324465843234L;

	@Id
	@Column(name = "USERNAME",length = 40)
	private String username;

	@Column(name = "PASSWORD",length = 32)
	private String password;
	
	@Column(name = "PASSWORDS",length = 40)
	private String passwords;

	@Column(name = "EMAIL",length = 100)
	private String email;

	@Column(name = "FIRST_NAME",length = 100)
	private String firstName;

	@Column(name = "LAST_NAME",length = 100)
	private String lastName;

	@Column(name = "TYPE")
	private Integer type;
	
	@Column(name = "FLAG")
	private Boolean flag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPE", insertable = false, updatable = false)
	private Authority authority;
	
	@Transient
	private List<Authority> authorities;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	

	public List<GrantedAuthority> getGrantedAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		/* add default authority for logged in user */
		grantedAuthorities.add(new SimpleGrantedAuthority(this.authority.getAuthority()));
		return grantedAuthorities;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

}
