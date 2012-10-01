package th.ac.kbu.cs.ExamProject.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import th.ac.kbu.cs.ExamProject.Service.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/application-context.xml"})
public class CreateFolderTest {
	
	@Autowired
	private FileService fileService;
	
}
