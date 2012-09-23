package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class SectionDomain extends SectionPrototype{
	

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
