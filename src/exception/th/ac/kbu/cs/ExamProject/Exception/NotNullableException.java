package th.ac.kbu.cs.ExamProject.Exception;

public class NotNullableException extends RuntimeException{

	private static final long serialVersionUID = 3086963935176322610L;

	private String message;
	
	public NotNullableException(String message){
		this.message = message;
	}
}
