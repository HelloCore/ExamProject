package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEACHER")
public class Teacher implements Serializable{
	
	private static final long serialVersionUID = -2299570542029910301L;

	@Id
	@Column(name = "USERNAME" ,length = 32)
	private String username;
	
	@Column(name = "PASSWORD" ,length = 32)
	private String password;

	@Column(name = "FIRST_NAME" ,length = 100)
	private String firstName;

	@Column(name = "LAST_NAME" ,length = 100)
	private String lastName;

	@Column(name = "PHONE" ,length = 12)
	private String phone;

	@Column(name = "EMAIL" ,length = 100)
	private String email;

	@Column(name = "TYPE" )
	private Long type;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Teacher[username="+this.username
				+" ,password="+this.password
				+" ,firstName="+this.firstName
				+" ,lastName="+this.lastName
				+" ,phone="+this.phone
				+" ,email="+this.email
				+" ,type="+this.type.toString()
				+" ]";
	}
}
