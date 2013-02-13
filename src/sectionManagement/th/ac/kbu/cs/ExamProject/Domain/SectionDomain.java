package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Entity.MasterSection;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class SectionDomain extends SectionPrototype{
	
	@Autowired
	private BasicFinderService basicFinderService;
	
	public MasterSection getMasterSection(){
		DetachedCriteria criteria = DetachedCriteria.forClass(MasterSection.class,"masterSection");
		criteria.add(Restrictions.eq("masterSection.status", 1));
		criteria.add(Restrictions.eq("masterSection.flag", true));
		return this.basicFinderService.findUniqueByCriteria(criteria);
	}

	public CoreGrid<HashMap<String,Object>> search(SectionCoreGridManager gridManager){
		return gridManager.search(this,SecurityUtils.getUsername());
	}

	public void save(SectionCoreGridManager gridManager) {
		gridManager.save(this);
	}
	
	public void delete(SectionCoreGridManager gridManager){
		gridManager.delete(this);
	}

}
