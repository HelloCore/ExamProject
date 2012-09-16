package th.ac.kbu.cs.ExamProject.Exception;

public class ExamPermissionDeniedException extends ExamException{

	private static final long serialVersionUID = -8503655188529518431L;
	
	public ExamPermissionDeniedException(String message){
		super(message);
	}
}
