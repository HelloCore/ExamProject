package th.ac.kbu.cs.ExamProject.Domain;

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
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Configurable
public class RegisterManagementDomain extends RegisterManagementPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
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
}
