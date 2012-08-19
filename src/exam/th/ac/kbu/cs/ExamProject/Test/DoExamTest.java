package th.ac.kbu.cs.ExamProject.Test;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import th.ac.kbu.cs.ExamProject.Domain.DoExamDomain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/application-context.xml"})
public class DoExamTest {
	
	public static DoExamDomain doExamDomain;
	
	@BeforeClass
	public static void beforeClass(){
		doExamDomain = new DoExamDomain();
		doExamDomain.setExamId(4L);
		doExamDomain.setNumOfQuestion(12);
	}
	
	@Test
	public void testCreateExam() throws Exception{
		HashMap<Long,Integer> numOfQuestion = new HashMap<Long,Integer>();
		numOfQuestion.put(1L, 0);
		numOfQuestion.put(2L, 2);
		numOfQuestion.put(3L, 1);
		numOfQuestion.put(4L, 0);
		numOfQuestion.put(5L, 3);
		
		HashMap<Long,Float> fragment = new HashMap<Long,Float>();
		fragment.put(1L, 0.20F);
		fragment.put(2L, 0.25F);
		fragment.put(3L, 0.30F);
		fragment.put(4L, 0.10F);
		fragment.put(5L, 0.15F);
		
		Integer size = 3;
		
		doExamDomain.updateNumOfQuestion(numOfQuestion, fragment, size);
		
		for(Long key : numOfQuestion.keySet()){
			System.out.println("key : " + key + " num : "+ numOfQuestion.get(key));
		}
	}
}
