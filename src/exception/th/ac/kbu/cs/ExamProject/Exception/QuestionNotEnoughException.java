package th.ac.kbu.cs.ExamProject.Exception;

public class QuestionNotEnoughException extends ExamException{

	private static final long serialVersionUID = 104780738786414678L;

	
	public QuestionNotEnoughException(String message){
		super(message);
	}
}
