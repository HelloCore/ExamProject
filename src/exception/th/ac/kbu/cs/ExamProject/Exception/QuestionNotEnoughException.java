package th.ac.kbu.cs.ExamProject.Exception;

public class QuestionNotEnoughException extends RuntimeException{

	private static final long serialVersionUID = 104780738786414678L;

	private String message;
	
	public QuestionNotEnoughException(String message){
		this.message = message;
	}
}
