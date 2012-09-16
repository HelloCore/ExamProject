package th.ac.kbu.cs.ExamProject.Exception;

public class YearInvalidException extends RuntimeException{


	private static final long serialVersionUID = -3493249399076571165L;

	public YearInvalidException(String message){
		super(message);
	}
}