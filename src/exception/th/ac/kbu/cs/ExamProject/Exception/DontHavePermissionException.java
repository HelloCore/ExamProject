package th.ac.kbu.cs.ExamProject.Exception;

public class DontHavePermissionException extends MainException{

	
	private static final long serialVersionUID = 730736907263166395L;

	public DontHavePermissionException(String message){
		super(message);
	}
}
