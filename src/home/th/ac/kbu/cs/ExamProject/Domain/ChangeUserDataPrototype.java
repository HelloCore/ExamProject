package th.ac.kbu.cs.ExamProject.Domain;

public class ChangeUserDataPrototype {
	private String oldPassword;
	private String newPassword;
	private String retypePassword;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private Integer prefixName;
	
	public Integer getPrefixName() {
		return prefixName;
	}
	public void setPrefixName(Integer prefixName) {
		this.prefixName = prefixName;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRetypePassword() {
		return retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}
	
}
