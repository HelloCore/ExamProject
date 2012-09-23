package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class ExamDomain extends ExamPrototype {

	public CoreGrid<HashMap<String, Object>> search(ExamCoreGridManager gridManager) {
		return gridManager.search(this,SecurityUtils.getUsername());
	}

	public void delete(ExamCoreGridManager gridManager) {
		gridManager.delete(this);
	}
	
}
