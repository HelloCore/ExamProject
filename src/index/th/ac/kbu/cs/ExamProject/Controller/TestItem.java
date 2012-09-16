package th.ac.kbu.cs.ExamProject.Controller;

import org.springframework.stereotype.Service;

@Service
public class TestItem implements TestItemInteface{
	
	public String convertToUpperCase( String input) {
		return input.toUpperCase();
	}

	public String getEntity(String input) {
		ExampleEntity entity = new ExampleEntity();
		entity.setMessage("5555");
		return entity.getMessage();
	}
	
	
}
