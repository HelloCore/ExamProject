package th.ac.kbu.cs.ExamProject.Exception;

public class CantExamEnoughException extends ExamException{

	private static final long serialVersionUID = 4957994926572095155L;
	
	public CantExamEnoughException(String message){
		super(message);
	}
}
