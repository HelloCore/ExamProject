package th.ac.kbu.cs.ExamProject.Exception;

public class CantActiveAnymoreException extends SignUpException{

	private static final long serialVersionUID = 5114572777159582100L;
	public CantActiveAnymoreException(String message){
		super(message);
	}
}
