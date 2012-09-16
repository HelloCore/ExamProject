package th.ac.kbu.cs.ExamProject.Exception;

public class CantEditExamException extends ExamException{

	private static final long serialVersionUID = 7111851074851727268L;
	
	public CantEditExamException(String message){
		super(message);
	}
}
