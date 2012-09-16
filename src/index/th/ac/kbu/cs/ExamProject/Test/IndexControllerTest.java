package th.ac.kbu.cs.ExamProject.Test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/application-context.xml"})
public class IndexControllerTest {
	
	private String validateMessage(String message){
		String errMessage = null;
		Integer length = message.length();
		if(message.substring(length-7,length).equals("'EMAIL'")){
			errMessage = "มีผู้ใช้ Email นี้แล้ว";
		}else if (message.substring(length-10,length).equals("'USERNAME'")){
			errMessage = "รหัสนักศึกษาซ้ำ กรุณาติดต่ออาจารย์ผู้สอน";
		}else{
			errMessage = message;
		}
		return errMessage;
	}
	
	@Test
	public void testValidateEmail() throws Exception{
		String message = "Duplicate entry 'aukwat@hotmail.com' for key 'EMAIL'";
		Assert.assertEquals("มีผู้ใช้ Email นี้แล้ว",this.validateMessage(message));
	}

	@Test
	public void testValidateStudentId() throws Exception{
		String message = "Duplicate entry '520702478971' for key 'USERNAME'";
		Assert.assertEquals("รหัสนักศึกษาซ้ำ กรุณาติดต่ออาจารย์ผู้สอน",this.validateMessage(message));
	}
	
	
}
