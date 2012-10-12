package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import th.ac.kbu.cs.ExamProject.Entity.AssignmentFile;
import th.ac.kbu.cs.ExamProject.Entity.AssignmentWork;
import th.ac.kbu.cs.ExamProject.Service.AssignmentService;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;

@Service
public class AssignmentServiceImpl implements AssignmentService{
	
	@Autowired
	private BasicEntityService basicEntityService;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void submitAssignment(AssignmentWork assignmentWork,
			List<MultipartFile> uploadFile) throws IOException {
		Long assignmentWorkId = (Long)this.basicEntityService.save(assignmentWork);
		
		for(MultipartFile multipartFile : uploadFile){
			if(multipartFile.getSize() > 0L){
				AssignmentFile assignmentFile = new AssignmentFile();
				assignmentFile.setAssignmentWorkId(assignmentWorkId);
				assignmentFile.setContent(multipartFile.getBytes());
				assignmentFile.setContentType(multipartFile.getContentType());
				assignmentFile.setFileName(multipartFile.getOriginalFilename());
				
				this.basicEntityService.save(assignmentFile);
			}
		}
		
	}


}
