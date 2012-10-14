package th.ac.kbu.cs.ExamProject.Domain;

import org.springframework.web.multipart.MultipartFile;

public class ContentPrototype {
	private Long courseId;
	private String currentPath;
	private Long contentFileId;
	private Long folderId;
	private String folderName;
	private String folderDesc;
	
	private String fileName;
	private String fileDesc;
	private MultipartFile fileData;
	
	private Long deleteFolderId;
	private Long deleteFileId;
	
	private Long from;
	
	public Long getFrom() {
		return from;
	}
	public void setFrom(Long from) {
		this.from = from;
	}
	public Long getContentFileId() {
		return contentFileId;
	}
	public void setContentFileId(Long contentFileId) {
		this.contentFileId = contentFileId;
	}
	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getCurrentPath() {
		return currentPath;
	}
	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}
	public String getFolderDesc() {
		return folderDesc;
	}
	public void setFolderDesc(String folderDesc) {
		this.folderDesc = folderDesc;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public MultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
	public Long getDeleteFolderId() {
		return deleteFolderId;
	}
	public void setDeleteFolderId(Long deleteFolderId) {
		this.deleteFolderId = deleteFolderId;
	}
	public Long getDeleteFileId() {
		return deleteFileId;
	}
	public void setDeleteFileId(Long deleteFileId) {
		this.deleteFileId = deleteFileId;
	}

	
}
