package th.ac.kbu.cs.ExamProject.Exception;

public class CantExamEnoughException extends RuntimeException{

	private static final long serialVersionUID = 4957994926572095155L;

	private String message;
	
	public CantExamEnoughException(String message){
		this.message = message;
	}
}
