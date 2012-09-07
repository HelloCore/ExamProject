package th.ac.kbu.cs.ExamProject.Exception;

public class DataNotMatchException extends SignUpException{

	private static final long serialVersionUID = -1097560898523826496L;

	public DataNotMatchException(String message){
		super(message);
	}
}
