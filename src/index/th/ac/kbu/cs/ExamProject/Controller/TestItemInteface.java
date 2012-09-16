package th.ac.kbu.cs.ExamProject.Controller;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestItemInteface {

	@NotNull(message="Null returns are not permitted") 
	String convertToUpperCase(@NotEmpty(message="Input must not be null or empty.") 
	                          String input);

	
	String getEntity(@NotEmpty(message="Input must not be null or empty.") 
    String input);
}
