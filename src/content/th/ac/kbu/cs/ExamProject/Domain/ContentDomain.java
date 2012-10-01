package th.ac.kbu.cs.ExamProject.Domain;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

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
	
	public List<HashMap<String,Object>> getFolderData(Long pathId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentPath.class,"contentPath");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("contentPath.contentPathId"),"contentPathId");
		projectionList.add(Projections.property("contentPath.contentPathName"),"contentPathName");
		projectionList.add(Projections.property("contentPath.contentPathDesc"),"contentPathDesc");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("contentPath.parentPathId", pathId));
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
		projectionList.add(Projections.property("contentFile.contentFileSize"),"contentFileSize");
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("contentFile.contentPathId", pathId));
		criteria.addOrder(Order.asc("contentFile.contentFileName"));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return basicFinderService.findByCriteria(criteria);
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
	
	public void uploadFile(HttpServletRequest request) {
		System.out.println("1");
		validateUploadFile();
		System.out.println("2");
		ContentPath contentPath = getCurrentData(this.getFolderId()); 
		System.out.println("3");
		if(this.getFolderId().equals(1L)){
			if(SecurityUtils.getUser().getType().equals(1)){
				ContentFileException ex = new ContentFileException("dont have permission");
				ex.setFolderId(getFolderId());
				throw ex;
			}
		}else{
			studentTeacherService.validateCourse(SecurityUtils.getUsername(), contentPath.getCourseId());
		}; 
		System.out.println("4");
		String newFilePathLocation = contentPath.getContentPathLocation()+this.getFileData().getOriginalFilename();
		String realPathLocation = request.getSession().getServletContext().getRealPath(newFilePathLocation);; 
		System.out.println("5");
		try {
			File file = new File(realPathLocation);; 
			System.out.println("6");
			
			String folderPath = request.getSession().getServletContext().getRealPath(contentPath.getContentPathLocation());

			System.out.println("6.1");
			if(!fileService.exists(folderPath)){
				System.out.println("6.2");
				fileService.makeDirectory(folderPath);
			}
			
			System.out.println("6.3");
			
			if(file.exists()){
				ContentFileException ex = new ContentFileException("File is exists");
				ex.setFolderId(getFolderId());
				throw ex;
			}
			; 
			System.out.println("7");
			this.getFileData().transferTo(file);; 
			System.out.println("8");
			ContentFile contentFile = new ContentFile();
			contentFile.setContentFileName(this.getFileName());
			contentFile.setContentFilePath(newFilePathLocation);
			contentFile.setContentFileSize(this.getFileData().getSize());
			contentFile.setContentFileDesc(this.getFileDesc());
			contentFile.setContentPathId(this.getFolderId());
			contentFile.setCreateBy(SecurityUtils.getUsername());
			contentFile.setCreateDate(new Date());
			
			this.basicEntityService.save(contentFile);
		} catch (Exception e) {
			ContentFileException ex = new ContentFileException(e.getMessage());
			ex.setFolderId(getFolderId());
			throw ex;
		} 
	}
	
}
