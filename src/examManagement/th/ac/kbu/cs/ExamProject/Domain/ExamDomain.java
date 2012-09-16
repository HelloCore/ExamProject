package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;

public class ExamDomain extends ExamPrototype {

	public CoreGrid<HashMap<String, Object>> searchAdmin(ExamCoreGridManager gridManager) {
		return gridManager.searchAdmin(this);
	}

	public void delete(ExamCoreGridManager gridManager) {
		gridManager.delete(this);
	}
	
}
