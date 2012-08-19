package th.ac.kbu.cs.ExamProject.Exception;

public class ExamNotStartedException extends RuntimeException{

	private static final long serialVersionUID = 2357997874832056387L;

	private String message;
	
	public ExamNotStartedException(String message){
		this.message = message;
	}
}
