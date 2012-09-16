package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;

@Configurable
public class QuestionGroupDomain extends QuestionGroupPrototype{

	public CoreGrid<HashMap<String,Object>> searchAdmin(QuestionGroupCoreGridManager gridManager){
		return gridManager.searchAdmin(this);
	}

	public void save(QuestionGroupCoreGridManager gridManager) {
		gridManager.save(this);
	}

	public void delete(QuestionGroupCoreGridManager gridManager) {
		gridManager.delete(this);
	}
	
}
