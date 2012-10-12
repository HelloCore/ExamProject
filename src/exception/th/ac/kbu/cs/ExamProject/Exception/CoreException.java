package th.ac.kbu.cs.ExamProject.Exception;

public class CoreException extends RuntimeException{

	private static final long serialVersionUID = 6305679744710597524L;
	
	protected CoreExceptionMessage coreExceptionMessage;
	
	public CoreException(CoreExceptionMessage coreExceptionMessage){
		this.coreExceptionMessage = coreExceptionMessage;
	}
	
	public String getMessage(){
		return this.coreExceptionMessage.getMessage();
	}

}
