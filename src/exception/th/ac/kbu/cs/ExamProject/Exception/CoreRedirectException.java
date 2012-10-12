package th.ac.kbu.cs.ExamProject.Exception;

public class CoreRedirectException extends CoreException{

	private String path;
	
	public CoreRedirectException(CoreExceptionMessage coreExceptionMessage){
		super(coreExceptionMessage);
	}
	
	public CoreRedirectException(CoreExceptionMessage coreExceptionMessage,String path){
		super(coreExceptionMessage);
		this.setPath(path);
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private static final long serialVersionUID = 4532112641584658534L;
	
	
	
}
