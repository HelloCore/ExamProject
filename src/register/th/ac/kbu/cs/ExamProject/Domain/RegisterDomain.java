package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;
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

import th.ac.kbu.cs.ExamProject.Entity.Register;
import th.ac.kbu.cs.ExamProject.Entity.Section;
import th.ac.kbu.cs.ExamProject.Entity.StudentSection;
import th.ac.kbu.cs.ExamProject.Exception.DataInValidException;
import th.ac.kbu.cs.ExamProject.Exception.DuplicateCourseException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Configurable
public class RegisterDomain extends RegisterPrototype{

	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	
	private void validateData(String username){
		if(BeanUtils.isEmpty(this.getCourseId()) 
				|| BeanUtils.isEmpty(this.getSectionId())){
			throw new ParameterNotFoundException("Parameter not found");
		}
		validateSectionData(this.getCourseId(),this.getSectionId());
		validateCourse(this.getCourseId(),username);
	}
	
	private void validateSectionData(Long courseId,Long sectionId){
		DetachedCriteria criteria = DetachedCriteria.forClass(Section.class,"section");
		criteria.createAlias("section.course", "course");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount());
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("section.sectionId",sectionId));
		criteria.add(Restrictions.eq("course.courseId",courseId));

		Long rowCount = basicFinderService.findUniqueByCriteria(criteria);
		if(rowCount != 1){
			throw new DataInValidException("Data is invalid!");
		}
	}
	private void validateCourse(Long courseId,String username){
		DetachedCriteria criteria = DetachedCriteria.forClass(StudentSection.class,"studentSection");
		criteria.createAlias("studentSection.section", "section");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount());
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("studentSection.username", username));
		criteria.add(Restrictions.eq("section.courseId", courseId));
		
		Long rowCount = basicFinderService.findUniqueByCriteria(criteria);
		if(rowCount> 0){
			throw new DuplicateCourseException("Course is duplicate!");
		}
	}
	
	public void register(String username) {
		validateData(username);
		Register register = this.toEntity(username);
		basicEntityService.save(register);
	}
	
	private Register toEntity(String username){
		Register registerInfo = new Register();
		registerInfo.setRequestDate(new Date());
		if(BeanUtils.isNotEmpty(this.getSectionId())){
			registerInfo.setSectionId(this.getSectionId());
		}
		registerInfo.setUsername(username);
		registerInfo.setStatus(0);
		return registerInfo;
	}

	public List<HashMap<String, Object>> getRegisterTable(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Register.class,"register");
		criteria.createAlias("register.section", "section");
		criteria.createAlias("section.course","course");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("register.registerId"),"registerId");
		projectionList.add(Projections.property("register.sectionId"),"sectionId");
		projectionList.add(Projections.property("section.sectionName"),"sectionName");
		projectionList.add(Projections.property("section.sectionSemester"),"sectionSemester");
		projectionList.add(Projections.property("section.sectionYear"),"sectionYear");
		projectionList.add(Projections.property("register.requestDate"),"requestDate");
		projectionList.add(Projections.property("register.status"),"status");
		projectionList.add(Projections.property("course.courseCode"),"courseCode");
		
		criteria.setProjection(projectionList);
		criteria.add(Restrictions.eq("register.username", username));
		
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		criteria.addOrder(Order.asc("register.requestDate"));
		
		return basicFinderService.findByCriteria(criteria);
	}
	
	private void validateCancelData(){
		if(BeanUtils.isEmpty(this.getRegisterId())){
			throw new ParameterNotFoundException("Parameter not found");
		}
	}
	
	private Register getAndValidateRegister(String username){
		DetachedCriteria criteria = DetachedCriteria.forClass(Register.class,"register");
		criteria.add(Restrictions.eq("register.registerId", this.getRegisterId()));
		Register register = basicFinderService.findUniqueByCriteria(criteria);
		
		if(!register.getUsername().equals(username)){
			throw new DataInValidException("Data is invalid");
		}
		return register;
	}
	
	public void cancelRegister(String username) {
		validateCancelData();
		Register register = this.getAndValidateRegister(username);
		basicEntityService.delete(register);
	}

}
