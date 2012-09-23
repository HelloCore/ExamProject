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
import th.ac.kbu.cs.ExamProject.Exception.DataInValidException;
import th.ac.kbu.cs.ExamProject.Exception.DontHavePermissionException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
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
			throw new ParameterNotFoundException("parameter not found!");
		}
	}
	
	public List<HashMap<String,Object>> getRegisterTable(){
		validateData();
		DetachedCriteria criteria = DetachedCriteria.forClass(Register.class,"register");
		criteria.createAlias("register.section", "section");
		criteria.createAlias("section.course", "course");
		criteria.createAlias("register.user", "user");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("register.registerId"),"registerId");
		projectionList.add(Projections.property("register.sectionId"),"sectionId");
		projectionList.add(Projections.property("register.requestDate"),"requestDate");
		projectionList.add(Projections.property("section.sectionName"),"sectionName");
		projectionList.add(Projections.property("section.sectionYear"),"sectionYear");
		projectionList.add(Projections.property("section.sectionSemester"),"sectionSemester");
		projectionList.add(Projections.property("user.username"),"studentId");
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
			throw new ParameterNotFoundException("parameter not found!");
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
			throw new DontHavePermissionException("dont have permission");
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
				throw new DataInValidException("Data invalid!");
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

	
	
	
}
