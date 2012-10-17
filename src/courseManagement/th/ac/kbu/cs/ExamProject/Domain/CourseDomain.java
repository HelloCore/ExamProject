package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class CourseDomain extends CoursePrototype{

	public CoreGrid<HashMap<String, Object>> search(
			CourseCoreGridManager gridManager) {
		return gridManager.search(this, SecurityUtils.getUsername());
	}

	public void save(CourseCoreGridManager gridManager) {
		gridManager.save(this);
	}

	public void delete(CourseCoreGridManager gridManager) {
		gridManager.delete(this);
	}
	
}
