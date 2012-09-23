package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

public class QuestionDomain extends QuestionPrototype{
	public CoreGrid<HashMap<String,Object>> search(QuestionCoreGridManager gridManager){
		 return gridManager.search(this,SecurityUtils.getUsername());
	}

	public void delete(QuestionCoreGridManager gridManager) {
		gridManager.delete(this);
	}
}
