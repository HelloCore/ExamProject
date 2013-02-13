package th.ac.kbu.cs.ExamProject.Domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Entity.Register;
import th.ac.kbu.cs.ExamProject.Entity.TeacherCourse;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.RegisterService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class RegisterManagementDomain extends RegisterManagementPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private RegisterService registerService;
	
	private void validateData(){
		if( BeanUtils.isEmpty(this.getCourseId()) 
				|| BeanUtils.isEmpty(this.getSectionId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public List<HashMap<String,Object>> getRegisterTable(){
		validateData();
		DetachedCriteria criteria = DetachedCriteria.forClass(Register.class,"register");
		criteria.createAlias("register.section", "section");
		criteria.createAlias("section.course", "course");
		criteria.createAlias("register.user", "user");
		criteria.createAlias("user.prefixName", "prefixName");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("register.registerId"),"registerId");
		projectionList.add(Projections.property("register.sectionId"),"sectionId");
		projectionList.add(Projections.property("register.requestDate"),"requestDate");
		projectionList.add(Projections.property("section.sectionName"),"sectionName");
		projectionList.add(Projections.property("section.sectionYear"),"sectionYear");
		projectionList.add(Projections.property("section.sectionSemester"),"sectionSemester");
		projectionList.add(Projections.property("user.username"),"studentId");
		projectionList.add(Projections.property("prefixName.prefixNameTh"),"prefixNameTh");
		projectionList.add(Projections.property("user.firstName"),"firstName");
		projectionList.add(Projections.property("user.lastName"),"lastName");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.or(Restrictions.eq("register.status", 0), Restrictions.eq("register.status", 3)));
		if(this.getCourseId().intValue() != 0){
			criteria.add(Restrictions.eq("course.courseId", this.getCourseId()));
		}
		if(this.getSectionId().intValue() != 0){
			criteria.add(Restrictions.eq("section.sectionId", this.getSectionId()));
		}
		criteria.addOrder(Order.asc("register.requestDate"));
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return basicFinderService.findByCriteria(criteria);
	}
	
	private void validateChangeSection(String username) throws JsonParseException, JsonMappingException, IOException{
		if( BeanUtils.isEmpty(this.getCourseId()) 
				|| BeanUtils.isNull(this.getRegisterIdArray())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);	
		}
		
		if(BeanUtils.isNotNull(username)){
			this.validateTeacherPermission(username);
		}
	}
	
	private void validateTeacherPermission(String username) throws JsonParseException, JsonMappingException, IOException{
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherCourse.class,"teacherCourse");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount());
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("teacherCourse.username", username));
		criteria.add(Restrictions.eq("teacherCourse.courseId", this.getCourseId()));
	
		Long result = basicFinderService.findUniqueByCriteria(criteria);
		
		if(result <= 0L){
			throw new CoreException(CoreExceptionMessage.PERMISSION_DENIED);
		}
		
		DetachedCriteria registerCriteria = DetachedCriteria.forClass(Register.class,"register");
		registerCriteria.createAlias("register.section", "section");
		registerCriteria.createAlias("section.course", "course");
		
		ProjectionList registerProjectionList = Projections.projectionList();
		registerProjectionList.add(Projections.property("course.courseId"),"courseId");
		registerCriteria.setProjection(registerProjectionList);
		
		registerCriteria.add(Restrictions.in("register.registerId", convertToList()));
		registerCriteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		List<HashMap<String,Object>> records = basicFinderService.findByCriteria(registerCriteria);
		for(HashMap<String,Object> record: records){
			if(!record.get("courseId").equals(this.getCourseId())){
				throw new CoreException(CoreExceptionMessage.INVALID_DATA);
				
			}
		}
	}
	
	private List<Long> convertToList() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		List<Long> results = mapper.readValue(this.getRegisterIdArray(), new TypeReference<ArrayList<Long>>(){});
		return results;
	}
	
	public void acceptSection(String username) throws JsonParseException, JsonMappingException, IOException{
		this.validateChangeSection(username);
		DetachedCriteria criteria = DetachedCriteria.forClass(Register.class,"register");
		criteria.add(Restrictions.in("register.registerId", convertToList()));
		List<Register> results = basicFinderService.findByCriteria(criteria);
		registerService.acceptSection(results);
	}
	public void rejectSection(String username) throws JsonParseException, JsonMappingException, IOException{
		this.validateChangeSection(username);
		DetachedCriteria criteria = DetachedCriteria.forClass(Register.class,"register");
		criteria.add(Restrictions.in("register.registerId", convertToList()));
		List<Register> results = basicFinderService.findByCriteria(criteria);
		registerService.rejectSection(results);
	}

	public CoreGrid<HashMap<String, Object>> search(
			RegisterCoreGridManagement gridManager) {
		return gridManager.search(this,SecurityUtils.getUsername());
	}

	public HashMap<String, Long> getRegisterSectionData() {
		if(BeanUtils.isEmpty(this.getSectionId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
		
		HashMap<String, Long> registerSectionData = new HashMap<String, Long>();
		

		DetachedCriteria totalCriteria = DetachedCriteria.forClass(Register.class,"register");
		totalCriteria.setProjection(Projections.rowCount());
		totalCriteria.add(Restrictions.eq("register.sectionId",this.getSectionId() ));
		
		registerSectionData.put("totalList", BeanUtils.toLong(this.basicFinderService.findUniqueByCriteria(totalCriteria)));
		
		DetachedCriteria pendingCriteria = DetachedCriteria.forClass(Register.class,"register");
		pendingCriteria.setProjection(Projections.rowCount());
		pendingCriteria.add(Restrictions.eq("register.sectionId",this.getSectionId() ));
		pendingCriteria.add(Restrictions.eq("register.status", 0));
		
		registerSectionData.put("pendingList", BeanUtils.toLong(this.basicFinderService.findUniqueByCriteria(pendingCriteria)));
		

		DetachedCriteria acceptCriteria = DetachedCriteria.forClass(Register.class,"register");
		acceptCriteria.setProjection(Projections.rowCount());
		acceptCriteria.add(Restrictions.eq("register.sectionId",this.getSectionId() ));
		acceptCriteria.add(Restrictions.eq("register.status", 1));

		registerSectionData.put("acceptList", BeanUtils.toLong(this.basicFinderService.findUniqueByCriteria(acceptCriteria)));
		

		DetachedCriteria deniedCriteria = DetachedCriteria.forClass(Register.class,"register");
		deniedCriteria.setProjection(Projections.rowCount());
		deniedCriteria.add(Restrictions.eq("register.sectionId",this.getSectionId() ));
		deniedCriteria.add(Restrictions.eq("register.status", 2));
		
		registerSectionData.put("deniedList", BeanUtils.toLong(this.basicFinderService.findUniqueByCriteria(deniedCriteria)));
		
		return registerSectionData;
	}

	
	
	
}
