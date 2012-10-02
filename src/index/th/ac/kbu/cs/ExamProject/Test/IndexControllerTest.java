package th.ac.kbu.cs.ExamProject.Test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:webContent/WEB-INF/application-context.xml"})
public class IndexControllerTest {
	
	@Test
	public void testValidateEmail() throws Exception{
		String contentType = "application/pdf";
		Boolean isMatch = contentType.matches(".*pdf.*");
		Assert.assertEquals(isMatch.booleanValue(),true);
	}

	
}
