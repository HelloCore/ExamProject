package th.ac.kbu.cs.ExamProject.Domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentTask;
import th.ac.kbu.cs.ExamProject.Exception.DataInValidException;
import th.ac.kbu.cs.ExamProject.Exception.ParameterNotFoundException;
import th.ac.kbu.cs.ExamProject.Service.TaskService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class TaskDomain extends TaskPrototype{
	
	@Autowired
	private TaskService taskService;
	
	private void validateAssignTaskData(){
		if(BeanUtils.isEmpty(this.getCourseId())
				|| BeanUtils.isEmpty(this.getSectionIdStr())
				|| BeanUtils.isEmpty(this.getTaskName())
				|| BeanUtils.isEmpty(this.getTaskDesc())
				|| BeanUtils.isEmpty(this.getStartDate())
				|| BeanUtils.isEmpty(this.getNumOfFile())
				|| BeanUtils.isEmpty(this.getLimitFileSizeKb())
				|| BeanUtils.isEmpty(this.getMaxScore())){
			throw new ParameterNotFoundException("parameter not found");
		}
	}
	
	private void validateTask(AssignmentTask assignmentTask){
		if(assignmentTask.getNumOfFile()>10 || assignmentTask.getNumOfFile() < 1){
			throw new DataInValidException("data invalid");
		}
		
		if(assignmentTask.getLimitFileSizeKb()> 204800L ||  assignmentTask.getLimitFileSizeKb() < 1){
			throw new DataInValidException("data invalid");
		}
		
		if(assignmentTask.getMaxScore() < 1 || assignmentTask.getMaxScore() > 10000){
			throw new DataInValidException("data invalid");
		}
	}
	
	public void assignTask(){
		validateAssignTaskData();
		SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ObjectMapper mapper = new ObjectMapper();
		
		AssignmentTask assignmentTask = new AssignmentTask();
		assignmentTask.setCourseId(this.getCourseId());
		assignmentTask.setAssignmentTaskName(this.getTaskName());
		assignmentTask.setAssignmentTaskDesc(this.getTaskDesc());
		List<Long> sectionIdList = new ArrayList<Long>();
		try {
			assignmentTask.setStartDate(parserSDF.parse(this.getStartDate()));
			assignmentTask.setEndDate(parserSDF.parse(this.getEndDate()));
			sectionIdList = mapper.readValue(this.getSectionIdStr(), new TypeReference<ArrayList<Long>>(){});
		} catch (Exception e) {
			throw new DataInValidException(e.getMessage());
		}
		System.out.println(sectionIdList.size());
		assignmentTask.setNumOfFile(this.getNumOfFile());
		assignmentTask.setLimitFileSizeKb(this.getLimitFileSizeKb());
		assignmentTask.setMaxScore(this.getMaxScore());
		assignmentTask.setCreateBy(SecurityUtils.getUsername());
		assignmentTask.setCreateDate(new Date());
		assignmentTask.setFlag(true);
		
		validateTask(assignmentTask);
		taskService.assignTask(assignmentTask, sectionIdList);
		
	}
}
