package th.ac.kbu.cs.ExamProject.Exception;

public class HibernateErrorException extends ExamException{
	private static final long serialVersionUID = -3050930643665585113L; 
	
	
	public HibernateErrorException(String message){
		super(message);
	}

}
