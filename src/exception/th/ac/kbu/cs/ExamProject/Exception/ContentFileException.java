package th.ac.kbu.cs.ExamProject.Exception;

public class ContentFileException extends MainException{

	private static final long serialVersionUID = -2099019771401768530L;

	private Long folderId;
	
	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public ContentFileException(String message){
		super(message);
	}
}
