package th.ac.kbu.cs.ExamProject.Exception;

public class ExamIsExpiredException extends RuntimeException{

	private static final long serialVersionUID = 2673840819384702649L;

	private String message;
	
	public ExamIsExpiredException(String message){
		this.message = message;
	}
}
