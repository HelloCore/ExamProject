package th.ac.kbu.cs.ExamProject.Exception;

public class DuplicateCourseException extends MainException {

	private static final long serialVersionUID = -9007777598807000565L;

	public DuplicateCourseException(String message){
		super(message);
	}
}