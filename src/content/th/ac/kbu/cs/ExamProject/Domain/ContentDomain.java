package th.ac.kbu.cs.ExamProject.Domain;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Entity.ContentFile;
import th.ac.kbu.cs.ExamProject.Entity.ContentPath;
import th.ac.kbu.cs.ExamProject.Exception.ContentFileException;
import th.ac.kbu.cs.ExamProject.Exception.DataInValidException;
import th.ac.kbu.cs.ExamProject.Exception.DontHavePermissionException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
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
	
	private List<Long> courseIdList;
	
	public List<HashMap<String,Object>> getFolderData(Long pathId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentPath.class,"contentPath");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("contentPath.contentPathId"),"contentPathId");
		projectionList.add(Projections.property("contentPath.contentPathName"),"contentPathName");
		projectionList.add(Projections.property("contentPath.contentPathDesc"),"contentPathDesc");
		projectionList.add(Projections.property("contentPath.viewCount"),"viewCount");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("contentPath.parentPathId", pathId));
		criteria.add(Restrictions.in("contentPath.courseId", this.getCourseIdList()));
		criteria.addOrder(Order.asc("contentPath.contentPathName"));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
	}
	public List<HashMap<String,Object>> getFileData(Long pathId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentFile.class,"contentFile");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("contentFile.contentFileId"),"contentFileId");
		projectionList.add(Projections.property("contentFile.contentFileName"),"contentFileName");
		projectionList.add(Projections.property("contentFile.contentFilePath"),"contentFilePath");
		projectionList.add(Projections.property("contentFile.contentFileDesc"),"contentFileDesc");
		projectionList.add(Projections.property("contentFile.contentFileType"),"contentFileType");
		projectionList.add(Projections.property("contentFile.contentFileSize"),"contentFileSize");
		projectionList.add(Projections.property("contentFile.viewCount"),"viewCount");
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("contentFile.contentPathId", pathId));
		criteria.addOrder(Order.asc("contentFile.contentFileName"));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
	}
	
	public ContentFile getFileDataEntity(Long fileId,HttpServletRequest request){
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentFile.class,"contentFile");
		criteria.createAlias("contentFile.contentPath", "contentPath");
		
		criteria.add(Restrictions.eq("contentFile.contentFileId", fileId));
		criteria.addOrder(Order.asc("contentFile.contentFileName"));
		
		ContentFile contentFile = basicFinderService.findUniqueByCriteria(criteria);
		
		if(BeanUtils.isNull(contentFile)){
			throw new DataInValidException("File not found!");
		}
		
		if(request.isUserInRole(RoleDescription.Property.STUDENT)){
			if(!studentTeacherService.validateStudentCourseId(SecurityUtils.getUsername(),contentFile.getContentPath().getCourseId())){
				throw new DontHavePermissionException("dont have permission");
			}
		}else if (request.isUserInRole(RoleDescription.Property.TEACHER)){
			if(!studentTeacherService.validateCourseId(SecurityUtils.getUsername(),contentFile.getContentPath().getCourseId())){
				throw new DontHavePermissionException("dont have permission");
			}
		}
		contentFile.setViewCount(contentFile.getViewCount() +1);
		this.basicEntityService.update(contentFile);
		
		return contentFile;
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
	public ContentPath getCurrentData(Long pathId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentPath.class,"contentPath");
		criteria.add(Restrictions.eq("contentPath.contentPathId", pathId));
		return basicFinderService.findUniqueByCriteria(criteria);
	}
	
	private void validateNewFolder(){
		if(BeanUtils.isEmpty(this.getFolderName())
				|| BeanUtils.isEmpty(this.getFolderId())){
			throw new ParameterNotFoundException("parameter not found!");
		}
	}
	
	
	public void newFolder(HttpServletRequest request) {
		validateNewFolder();
		ContentPath contentPath = getCurrentData(this.getFolderId()); 
		if(this.getFolderId().equals(1L)){
			if(SecurityUtils.getUser().getType().equals(1)){
				throw new DontHavePermissionException("dont have permission");
			}
		}else{
			studentTeacherService.validateCourse(SecurityUtils.getUsername(), contentPath.getCourseId());
		}
		String newFolderPathLocation = contentPath.getContentPathLocation()+this.getFolderName();
		String realPathLocation = request.getSession().getServletContext().getRealPath(newFolderPathLocation);
		
		System.out.println(realPathLocation);
		if(fileService.exists(realPathLocation)){
			throw new DataInValidException("Folder is exists");
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
				throw new DataInValidException("Can't Create Folder");
			}
		}
	}
	
	private void validateUploadFile(){
		if(BeanUtils.isEmpty(this.getFileName())
				|| BeanUtils.isEmpty(this.getFolderId())
				|| BeanUtils.isNull(this.getFileData())){
			ContentFileException ex = new ContentFileException("parameter not found!");
			ex.setFolderId(getFolderId());
			throw ex;
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
			ContentFileException ex = new ContentFileException("accept file type");
			ex.setFolderId(getFolderId());
			throw ex;
		}
	}
	public void uploadFile(HttpServletRequest request) {
		validateUploadFile();
		ContentPath contentPath = getCurrentData(this.getFolderId()); 
		if(this.getFolderId().equals(1L)){
			if(SecurityUtils.getUser().getType().equals(1)){
				ContentFileException ex = new ContentFileException("คุณไม่มีสิทธิ์เข้าถึงไฟล์นี้");
				ex.setFolderId(getFolderId());
				throw ex;
			}
		}else{
			studentTeacherService.validateCourse(SecurityUtils.getUsername(), contentPath.getCourseId());
		}; 
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
				ContentFileException ex = new ContentFileException("File is exists");
				ex.setFolderId(getFolderId());
				throw ex;
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
			ContentFileException ex = new ContentFileException(e.getMessage());
			ex.setFolderId(getFolderId());
			throw ex;
		} 
	}

	public void validatePermission(Long pathId) {
		ContentPath contentPath = this.getCurrentData(pathId);
		contentPath.setViewCount(contentPath.getViewCount() +1);
		
		this.basicEntityService.update(contentPath);
		
		if(!this.getCourseIdList().contains(contentPath.getCourseId())){
			ContentFileException ex = new ContentFileException("dont have permission");
			ex.setFolderId(1L);
			throw ex;
		}	
	}
	
	public List<Long> getCourseIdList() {
		return courseIdList;
	}
	
	public void setCourseIdList(HttpServletRequest request) {
		if(request.isUserInRole(RoleDescription.Property.TEACHER)){
			this.courseIdList = this.studentTeacherService.getCourseId(SecurityUtils.getUsername());
		}else if (request.isUserInRole(RoleDescription.Property.STUDENT)){
			this.courseIdList = this.studentTeacherService.getStudentCourseId(SecurityUtils.getUsername());
		}else{
			this.courseIdList = null;
		}
	}
	
	public void deleteFolder(HttpServletRequest request) {
		if(BeanUtils.isEmpty(this.getFolderId())
				|| BeanUtils.isEmpty(this.getFolderId())){
			ContentFileException ex = new ContentFileException("parameter not found");
			ex.setFolderId(this.getFolderId());
			throw ex;
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
			ContentFileException ex = new ContentFileException(e.getMessage());
			ex.setFolderId(this.getFolderId());
			throw ex;
		}
		
		ContentPath deleteContentPath = new ContentPath();
		deleteContentPath.setContentPathId(contentPath.getContentPathId());
		
		this.basicEntityService.delete(deleteContentPath);
		
	}
	
	public void deleteFile(HttpServletRequest request) {
		if(BeanUtils.isEmpty(this.getDeleteFileId())
				|| BeanUtils.isEmpty(this.getFolderId())){
			ContentFileException ex = new ContentFileException("parameter not found");
			ex.setFolderId(this.getFolderId());
			throw ex;
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentFile.class,"contentFile");
		criteria.add(Restrictions.eq("contentFile.contentFileId",this.getDeleteFileId()));
		
		ContentFile contentFile = basicFinderService.findUniqueByCriteria(criteria);
		
		String filePath = request.getSession().getServletContext().getRealPath(contentFile.getContentFilePath());
		File file = new File(filePath);
		if(file.exists()){
			if(!file.delete()){
				ContentFileException ex = new ContentFileException("cant delete file");
				ex.setFolderId(this.getFolderId());
				throw ex;
			}
		}
		
		ContentFile deleteContentFile = new ContentFile();
		deleteContentFile.setContentFileId(contentFile.getContentFileId());
		
		this.basicEntityService.delete(deleteContentFile);
	}
}
