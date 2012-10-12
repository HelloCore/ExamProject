package th.ac.kbu.cs.ExamProject.Exception;

public class CoreExceptionMessage {
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static final String PERMISSION_DENIED;
	public static final String PARAMETER_NOT_FOUND;
	public static final String INVALID_DATA;
	
	public static final String CANT_EDIT_EXAM;
	public static final String EXAM_EXPIRED;
	public static final String EXAM_NOT_STARTED;
	
	static{
		PERMISSION_DENIED = "PERMISSION_DENIED";
		PARAMETER_NOT_FOUND = "PARAMETER_NOT_FOUND";
		INVALID_DATA = "INVALID_DATA";
		CANT_EDIT_EXAM = "CANT_EDIT_EXAM";
		EXAM_EXPIRED = "EXAM_EXPIRED";
		EXAM_NOT_STARTED = "EXAM_NOT_STARTED";
	}
}
