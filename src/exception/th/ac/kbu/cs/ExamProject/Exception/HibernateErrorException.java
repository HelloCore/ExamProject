package th.ac.kbu.cs.ExamProject.Exception;

public class HibernateErrorException extends RuntimeException{
	private static final long serialVersionUID = -3050930643665585113L; 
	
	private String message;
	
	public HibernateErrorException(String message){
		this.message = message;
	}

}
