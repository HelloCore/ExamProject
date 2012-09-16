package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class SectionDomain extends SectionPrototype{
	
	public CoreGrid<HashMap<String,Object>> searchAdmin(SectionCoreGridManager gridManager){
		return gridManager.searchAdmin(this);
	}

	public CoreGrid<HashMap<String,Object>> searchTeacher(SectionCoreGridManager gridManager){
		return gridManager.searchTeacher(this,SecurityUtils.getUsername());
	}

	public void save(SectionCoreGridManager gridManager) {
		gridManager.save(this);
	}
	
	public void delete(SectionCoreGridManager gridManager){
		gridManager.delete(this);
	}

}
