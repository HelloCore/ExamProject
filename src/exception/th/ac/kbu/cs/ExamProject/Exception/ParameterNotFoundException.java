package th.ac.kbu.cs.ExamProject.Exception;

public class ParameterNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7201375877952097105L;

	private String message;
	
	public ParameterNotFoundException(String message){
		this.message = message;
	}
}
