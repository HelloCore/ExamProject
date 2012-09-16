package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;

public class QuestionDomain extends QuestionPrototype{
	public CoreGrid<HashMap<String,Object>> searchAdmin(QuestionCoreGridManager gridManager){
		 return gridManager.searchAdmin(this);
	}

	public void delete(QuestionCoreGridManager gridManager) {
		gridManager.delete(this);
	}
}
