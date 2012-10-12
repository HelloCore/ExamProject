package th.ac.kbu.cs.ExamProject.Exception;

public class CoreRedirectException extends CoreException{

	private String path;
	private String paramStr;
	
	public CoreRedirectException(CoreExceptionMessage coreExceptionMessage){
		super(coreExceptionMessage);
	}
	
	public CoreRedirectException(CoreExceptionMessage coreExceptionMessage,String path){
		super(coreExceptionMessage);
		this.setPath(path);
	}
	
	public CoreRedirectException(CoreExceptionMessage coreExceptionMessage,String path,String paramStr){
		super(coreExceptionMessage);
		this.setPath(path);
		this.setParamStr(paramStr);
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	private static final long serialVersionUID = 4532112641584658534L;
	
	
	
}
