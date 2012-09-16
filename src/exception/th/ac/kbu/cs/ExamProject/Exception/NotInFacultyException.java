package th.ac.kbu.cs.ExamProject.Exception;

public class NotInFacultyException extends RuntimeException{

	private static final long serialVersionUID = 2774786678022362161L;

	public NotInFacultyException(String message){
		super(message);
	}
}
