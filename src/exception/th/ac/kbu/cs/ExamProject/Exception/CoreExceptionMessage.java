package th.ac.kbu.cs.ExamProject.Exception;


public class CoreExceptionMessage {
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public CoreExceptionMessage(String message){
		this.message = message;
	}
	
	public static final CoreExceptionMessage PERMISSION_DENIED;
	public static final CoreExceptionMessage PARAMETER_NOT_FOUND;
	public static final CoreExceptionMessage INVALID_DATA;
	
	public static final CoreExceptionMessage CANT_EDIT_EXAM;
	public static final CoreExceptionMessage EXAM_EXPIRED;
	public static final CoreExceptionMessage EXAM_NOT_STARTED;
	public static final CoreExceptionMessage CANT_DO_EXAM_ANYMORE;
	public static final CoreExceptionMessage QUESTION_NOT_ENOUGH;
	public static final CoreExceptionMessage EXAM_NOT_COMPLETE;
	
	public static final CoreExceptionMessage DUPLICATE_COURSE;
	
	public static final CoreExceptionMessage INVALID_YEAR;
	public static final CoreExceptionMessage NOT_IN_FACULTY;
	public static final CoreExceptionMessage STUDENT_NOT_FOUND;
	public static final CoreExceptionMessage CANT_ACTIVE_ANYMORE;
	public static final CoreExceptionMessage INVALID_ACTIVE_CODE;
	public static final CoreExceptionMessage STUDENT_ID_AND_EMAIL_NOT_MATCH;

	public static final CoreExceptionMessage DUPLICATE_EMAIL;
	public static final CoreExceptionMessage DUPLICATE_STUDENT_ID_OR_EMAIL;
	
	public static final CoreExceptionMessage FILE_NOT_FOUND;
	public static final CoreExceptionMessage FOLDER_IS_EXISTS;
	public static final CoreExceptionMessage CANT_CREATE_FOLDER;
	
	public static final CoreExceptionMessage CANT_ACCEPT_FILE_TYPE;
	public static final CoreExceptionMessage FILE_IS_EXISTS;

	public static final CoreExceptionMessage CANT_DELETE_FILE;
	
	public static final CoreExceptionMessage ASSIGNMENT_NOT_STARTED;
	public static final CoreExceptionMessage ASSIGNMENT_EXPIRED;
	public static final CoreExceptionMessage LIMIT_NUM_OF_FILE;
	public static final CoreExceptionMessage LIMIT_FILE_SIZE;
	public static final CoreExceptionMessage CANT_SUBMIT_ANYMORE;
	
	public static final CoreExceptionMessage OLD_AND_NEW_PASSWORD_IS_DUPLICATE;
	public static final CoreExceptionMessage PASSWORD_IS_INVALID;
	public static final CoreExceptionMessage PASSWORD_IS_NOT_MATCH;
	
	
	static{
		PERMISSION_DENIED = new CoreExceptionMessage("PERMISSION_DENIED");
		PARAMETER_NOT_FOUND = new CoreExceptionMessage("PARAMETER_NOT_FOUND");
		INVALID_DATA = new CoreExceptionMessage("INVALID_DATA");
		
		CANT_EDIT_EXAM = new CoreExceptionMessage("CANT_EDIT_EXAM");
		EXAM_EXPIRED = new CoreExceptionMessage("EXAM_EXPIRED");
		EXAM_NOT_STARTED = new CoreExceptionMessage("EXAM_NOT_STARTED");
		CANT_DO_EXAM_ANYMORE = new CoreExceptionMessage("CANT_DO_EXAM_ANYMORE");
		QUESTION_NOT_ENOUGH = new CoreExceptionMessage("จำนวนคำถามไม่พอ");
		EXAM_NOT_COMPLETE = new CoreExceptionMessage("EXAM_NOT_COMPLETE");
		
		DUPLICATE_COURSE = new CoreExceptionMessage("DUPLICATE_COURSE");
		
		INVALID_YEAR = new CoreExceptionMessage("INVALID_YEAR");
		NOT_IN_FACULTY = new CoreExceptionMessage("NOT_IN_FACULTY");
		STUDENT_NOT_FOUND = new CoreExceptionMessage("STUDENT_NOT_FOUND");
		CANT_ACTIVE_ANYMORE = new CoreExceptionMessage("CANT_ACTIVE_ANYMORE");
		INVALID_ACTIVE_CODE = new CoreExceptionMessage("INVALID_ACTIVE_CODE");
		STUDENT_ID_AND_EMAIL_NOT_MATCH = new CoreExceptionMessage("STUDENT_ID_AND_EMAIL_NOT_MATCH");
		
		DUPLICATE_EMAIL = new CoreExceptionMessage("DUPLICATE_EMAIL");
		
		DUPLICATE_STUDENT_ID_OR_EMAIL = new CoreExceptionMessage("รหัสนักศึกษาหรืออีเมลล์นี้ มีผู้ใช้งานแล้ว");
		
		FILE_NOT_FOUND = new CoreExceptionMessage("FILE_NOT_FOUND");
		FOLDER_IS_EXISTS = new CoreExceptionMessage("FOLDER_IS_EXISTS");
		CANT_CREATE_FOLDER = new CoreExceptionMessage("CANT_CREATE_FOLDER");
		
		CANT_ACCEPT_FILE_TYPE = new CoreExceptionMessage("CANT_ACCEPT_FILE_TYPE");
		FILE_IS_EXISTS = new CoreExceptionMessage("FILE_IS_EXISTS");
		CANT_DELETE_FILE = new CoreExceptionMessage("CANT_DELETE_FILE");
		
		ASSIGNMENT_NOT_STARTED = new CoreExceptionMessage("ASSIGNMENT_NOT_STARTED");
		ASSIGNMENT_EXPIRED = new CoreExceptionMessage("ASSIGNMENT_EXPIRED");
		LIMIT_NUM_OF_FILE = new CoreExceptionMessage("LIMIT_NUM_OF_FILE");
		LIMIT_FILE_SIZE = new CoreExceptionMessage("LIMIT_FILE_SIZE");
		CANT_SUBMIT_ANYMORE = new CoreExceptionMessage("CANT_SUBMIT_ANYMORE");
		
		OLD_AND_NEW_PASSWORD_IS_DUPLICATE = new CoreExceptionMessage("OLD_AND_NEW_PASSWORD_IS_DUPLICATE");
		PASSWORD_IS_INVALID = new CoreExceptionMessage("PASSWORD_IS_INVALID");
		PASSWORD_IS_NOT_MATCH = new CoreExceptionMessage("PASSWORD_IS_NOT_MATCH");
	}
}
