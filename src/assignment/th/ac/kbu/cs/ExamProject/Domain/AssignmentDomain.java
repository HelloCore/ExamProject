package th.ac.kbu.cs.ExamProject.Domain;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Service.AssignmentService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class AssignmentDomain extends AssignmentPrototype{
	
	@Autowired
	private AssignmentService assignmentService;
	
	
	public List<Object[]> getAssignmentData(){
		return assignmentService.getAssignmentData(SecurityUtils.getUsername());
	}

	private void validateGetAssignmentDetail(){
		if(BeanUtils.isEmpty(this.getAssignmentId())){
			throw new ParameterNotFoundException("parameter not found");
		}
	}
	public HashMap<String, Object> getAssignmentDetail() {
		this.validateGetAssignmentDetail();
		return assignmentService.getAssignmentDetail(this.getAssignmentId(),SecurityUtils.getUsername());
	}

	public CoreGrid<HashMap<String, Object>> getAssignmentResult(
			AssignmentResultCoreGridManager gridManager) {
		return gridManager.search(this, SecurityUtils.getUsername());
	}
}
