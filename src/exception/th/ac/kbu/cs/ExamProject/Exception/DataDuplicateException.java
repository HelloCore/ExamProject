package th.ac.kbu.cs.ExamProject.Exception;

public class DataDuplicateException extends RuntimeException {

	private static final long serialVersionUID = -3212229297804858072L;

	public DataDuplicateException(String message){
		super(message);
	}
}
