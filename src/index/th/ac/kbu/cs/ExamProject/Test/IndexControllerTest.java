package th.ac.kbu.cs.ExamProject.Test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

import th.ac.kbu.cs.ExamProject.Controller.IndexController;
import th.ac.kbu.cs.ExamProject.Controller.TestItemInteface;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/application-context.xml"})
public class IndexControllerTest {
	
	@Autowired
	private TestItemInteface item;
	
	@Test
	public void testIndexController() throws Exception{
		IndexController controller = new IndexController();
		ModelMap map = controller.init(new ModelMap());
		Assert.assertEquals("55555",map.get("strTest"));
	}
	
	@Test
	public void testUpperItem()  throws Exception{
		Assert.assertEquals("ASDF", item.getEntity(null));
	}
	@Test
	public void testUpperItemNull()  throws Exception{
		Assert.assertEquals("ASDF", item.convertToUpperCase(null));
	}
	@Test
	public void testUpperItemBlank()  throws Exception{
		Assert.assertEquals("ASDF", item.convertToUpperCase(""));
	}
}
