package th.ac.kbu.cs.ExamProject.Domain;

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

import th.ac.kbu.cs.ExamProject.Description.PathDescription;
import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Entity.ContentFile;
import th.ac.kbu.cs.ExamProject.Entity.ContentPath;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Exception.CoreRedirectException;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;
@Configurable
public class ContentListDomain extends ContentListPrototype{

	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private StudentTeacherService studentTeacherService; 
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	private List<Long> courseIdList;
	
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
			throw new CoreRedirectException(CoreExceptionMessage.FILE_NOT_FOUND, PathDescription.Main.CONTENT);
		}
		
		if(request.isUserInRole(RoleDescription.Property.STUDENT)){
			if(!studentTeacherService.validateStudentCourseId(SecurityUtils.getUsername(),contentFile.getContentPath().getCourseId())){
				throw new CoreRedirectException(CoreExceptionMessage.PERMISSION_DENIED, PathDescription.Main.CONTENT);
			}
		}else if (request.isUserInRole(RoleDescription.Property.TEACHER)){
			if(!studentTeacherService.validateCourseId(SecurityUtils.getUsername(),contentFile.getContentPath().getCourseId())){
				throw new CoreRedirectException(CoreExceptionMessage.PERMISSION_DENIED, PathDescription.Main.CONTENT);
			}
		}
		contentFile.setViewCount(contentFile.getViewCount() +1);
		this.basicEntityService.update(contentFile);
		
		return contentFile;
	}

	public void validatePermission(Long pathId) {
		ContentPath contentPath = this.getCurrentData(pathId);
		contentPath.setViewCount(contentPath.getViewCount() +1);
		
		this.basicEntityService.update(contentPath);
		
		if(!this.getCourseIdList().contains(contentPath.getCourseId())){
			throw new CoreRedirectException(CoreExceptionMessage.PERMISSION_DENIED, PathDescription.Main.CONTENT, "path="+this.getFolderId());
		}	
	}
	
	public ContentPath getCurrentData(Long pathId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ContentPath.class,"contentPath");
		criteria.add(Restrictions.eq("contentPath.contentPathId", pathId));
		return basicFinderService.findUniqueByCriteria(criteria);
	}
	

}
