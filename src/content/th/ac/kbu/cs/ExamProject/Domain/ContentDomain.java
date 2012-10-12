package th.ac.kbu.cs.ExamProject.Domain;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Description.PathDescription;
import th.ac.kbu.cs.ExamProject.Entity.ContentFile;
import th.ac.kbu.cs.ExamProject.Entity.ContentPath;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Exception.CoreRedirectException;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.FileService;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class ContentDomain extends ContentPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private StudentTeacherService studentTeacherService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private BasicEntityService basicEntityService;

	public ContentPath getCurrentData(Long pathId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentPath.class,"contentPath");
		criteria.add(Restrictions.eq("contentPath.contentPathId", pathId));
		return basicFinderService.findUniqueByCriteria(criteria);
	}
	
	public Long getParentPath(Long pathId) {
		Long parentPath = null;
		if(pathId != 1L){
			DetachedCriteria criteria = DetachedCriteria.forClass(ContentPath.class,"contentPath");
			criteria.setProjection(Projections.property("contentPath.parentPathId"));
			criteria.add(Restrictions.eq("contentPath.contentPathId", pathId));
			parentPath = basicFinderService.findUniqueByCriteria(criteria);
		}else{
			parentPath = 0L;
		}
		return parentPath;
	}
	
	public Boolean checkCanEdit(Long pathId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentPath.class,"contentPath");
		criteria.setProjection(Projections.property("contentPath.courseId"));
		criteria.add(Restrictions.eq("contentPath.contentPathId", pathId));
		
		Long courseId = basicFinderService.findUniqueByCriteria(criteria);
		
		return studentTeacherService.validateCourseId(SecurityUtils.getUsername(), courseId);
	}
	private void validateNewFolder(){
		if(BeanUtils.isEmpty(this.getFolderName())
				|| BeanUtils.isEmpty(this.getFolderId())){
			throw new CoreRedirectException(CoreExceptionMessage.PARAMETER_NOT_FOUND, PathDescription.Main.CONTENT,"path="+this.getFolderId());
			
		}
	}
	
	
	public void newFolder(HttpServletRequest request) {
		validateNewFolder();
		ContentPath contentPath = getCurrentData(this.getFolderId()); 
		if(this.getFolderId().equals(1L)){
			if(SecurityUtils.getUser().getType().equals(1)){
				throw new CoreRedirectException(CoreExceptionMessage.PERMISSION_DENIED, PathDescription.Main.CONTENT,"path="+this.getFolderId());
			}
		}else{
			studentTeacherService.validateCourse(SecurityUtils.getUsername(), contentPath.getCourseId());
		}
		String newFolderPathLocation = contentPath.getContentPathLocation()+this.getFolderName();
		String realPathLocation = request.getSession().getServletContext().getRealPath(newFolderPathLocation);
		
		if(fileService.exists(realPathLocation)){
			throw new CoreRedirectException(CoreExceptionMessage.FOLDER_IS_EXISTS, PathDescription.Main.CONTENT,"path="+this.getFolderId());
		}else{
			if(fileService.makeDirectory(realPathLocation)){
				ContentPath newPath = new ContentPath();
				newPath.setContentPathLocation(newFolderPathLocation+"/");
				newPath.setContentPathName(this.getFolderName());
				newPath.setContentPathDesc(this.getFolderDesc());
				newPath.setParentPathId(contentPath.getContentPathId());
				newPath.setCourseId(contentPath.getCourseId());
				newPath.setViewCount(0);
				basicEntityService.save(newPath);
			}else{
				throw new CoreRedirectException(CoreExceptionMessage.CANT_CREATE_FOLDER, PathDescription.Main.CONTENT,"path="+this.getFolderId());
			}
		}
	}
	
	private void validateUploadFile(){
		if(BeanUtils.isEmpty(this.getFileName())
				|| BeanUtils.isEmpty(this.getFolderId())
				|| BeanUtils.isNull(this.getFileData())){
			throw new CoreRedirectException(CoreExceptionMessage.PARAMETER_NOT_FOUND, PathDescription.Main.CONTENT, "path="+this.getFolderId());
		}
	}
	
	private void validateFileType(ContentFile contentFile,String contentType){
		if(contentType.matches(".*word.*")){
			contentFile.setContentFileType(1);
		}else if (contentType.matches(".*excel.*")){
			contentFile.setContentFileType(2);
		}else if (contentType.matches(".*powerpoint.*")){
			contentFile.setContentFileType(3);
		}else if (contentType.matches(".*pdf.*")){
			contentFile.setContentFileType(4);
		}else if (contentType.matches(".*java.*")){
			contentFile.setContentFileType(5);
		}else if (contentType.matches(".*(zip|7z|rar).*")){
			contentFile.setContentFileType(6);
		}else if (contentType.matches(".*(text).*") 
				|| contentType.matches(".*(png|jpe?g|gif).*")){
			contentFile.setContentFileType(7);
		}else{
			throw new CoreRedirectException(CoreExceptionMessage.CANT_ACCEPT_FILE_TYPE, PathDescription.Main.CONTENT, "path="+this.getFolderId());
		}
	}
	public void uploadFile(HttpServletRequest request) {
		validateUploadFile();
		ContentPath contentPath = getCurrentData(this.getFolderId()); 
		if(this.getFolderId().equals(1L)){
			if(SecurityUtils.getUser().getType().equals(1)){
				throw new CoreRedirectException(CoreExceptionMessage.PERMISSION_DENIED, PathDescription.Main.CONTENT, "path="+this.getFolderId());
			}
		}else{
			studentTeacherService.validateCourse(SecurityUtils.getUsername(), contentPath.getCourseId());
		}
		ContentFile contentFile = new ContentFile();
		
		validateFileType(contentFile, this.getFileData().getContentType());
		String newFilePathLocation = contentPath.getContentPathLocation()+this.getFileData().getOriginalFilename();
		String realPathLocation = request.getSession().getServletContext().getRealPath(newFilePathLocation);; 
		try {
			File file = new File(realPathLocation);; 
			
			String folderPath = request.getSession().getServletContext().getRealPath(contentPath.getContentPathLocation());

			if(!fileService.exists(folderPath)){
				fileService.makeDirectory(folderPath);
			}
			
			if(file.exists()){
				throw new CoreRedirectException(CoreExceptionMessage.FILE_IS_EXISTS, PathDescription.Main.CONTENT, "path="+this.getFolderId());
			}
			this.getFileData().transferTo(file);
			contentFile.setContentFileName(this.getFileName());
			contentFile.setContentFilePath(newFilePathLocation);
			contentFile.setContentFileSize(this.getFileData().getSize());
			contentFile.setContentFileDesc(this.getFileDesc());
			contentFile.setContentPathId(this.getFolderId());
			contentFile.setCreateBy(SecurityUtils.getUsername());
			contentFile.setCreateDate(new Date());
			contentFile.setViewCount(0);
			this.basicEntityService.save(contentFile);
		} catch (Exception e) {
			throw new CoreRedirectException(new CoreExceptionMessage(e.getMessage()), PathDescription.Main.CONTENT, "path="+this.getFolderId());
		} 
	}

	
	public void deleteFolder(HttpServletRequest request) {
		if(BeanUtils.isEmpty(this.getFolderId())
				|| BeanUtils.isEmpty(this.getFolderId())){
			throw new CoreRedirectException(CoreExceptionMessage.PARAMETER_NOT_FOUND, PathDescription.Main.CONTENT, "path="+this.getFolderId());
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentPath.class,"contentPath");
		criteria.add(Restrictions.eq("contentPath.contentPathId", this.getDeleteFolderId()));
		
		ContentPath contentPath = basicFinderService.findUniqueByCriteria(criteria);

		String filePath = request.getSession().getServletContext().getRealPath(contentPath.getContentPathLocation());
		File file = new File(filePath);
		try {
			if(file.exists()){
				FileUtils.deleteDirectory(file);
			}
		}catch(Exception e){
			throw new CoreRedirectException(new CoreExceptionMessage(e.getMessage()), PathDescription.Main.CONTENT, "path="+this.getFolderId());
		}
		
		ContentPath deleteContentPath = new ContentPath();
		deleteContentPath.setContentPathId(contentPath.getContentPathId());
		
		this.basicEntityService.delete(deleteContentPath);
		
	}
	
	public void deleteFile(HttpServletRequest request) {
		if(BeanUtils.isEmpty(this.getDeleteFileId())
				|| BeanUtils.isEmpty(this.getFolderId())){
			throw new CoreRedirectException(CoreExceptionMessage.PARAMETER_NOT_FOUND, PathDescription.Main.CONTENT, "path="+this.getFolderId());
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentFile.class,"contentFile");
		criteria.add(Restrictions.eq("contentFile.contentFileId",this.getDeleteFileId()));
		
		ContentFile contentFile = basicFinderService.findUniqueByCriteria(criteria);
		
		String filePath = request.getSession().getServletContext().getRealPath(contentFile.getContentFilePath());
		File file = new File(filePath);
		if(file.exists()){
			if(!file.delete()){
				throw new CoreRedirectException(CoreExceptionMessage.CANT_DELETE_FILE, PathDescription.Main.CONTENT, "path="+this.getFolderId());	
			}
		}
		
		ContentFile deleteContentFile = new ContentFile();
		deleteContentFile.setContentFileId(contentFile.getContentFileId());
		
		this.basicEntityService.delete(deleteContentFile);
	}
}
