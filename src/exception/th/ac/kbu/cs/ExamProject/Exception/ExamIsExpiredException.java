package th.ac.kbu.cs.ExamProject.Exception;

public class ExamIsExpiredException extends ExamException{

	private static final long serialVersionUID = 2673840819384702649L;

	public ExamIsExpiredException(String message){
		super(message);
	}
}
