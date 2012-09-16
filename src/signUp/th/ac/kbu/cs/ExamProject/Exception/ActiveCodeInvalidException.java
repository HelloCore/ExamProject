package th.ac.kbu.cs.ExamProject.Exception;

public class ActiveCodeInvalidException extends SignUpException{

	private static final long serialVersionUID = -4728101827638350907L;

	public ActiveCodeInvalidException(String message){
		super(message);
	}
}
