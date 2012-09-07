package th.ac.kbu.cs.ExamProject.Exception;

public class NotFoundStudentException extends SignUpException{
	private static final long serialVersionUID = 4117541597413726114L;

	public NotFoundStudentException(String message){
		super(message);
	}
}
