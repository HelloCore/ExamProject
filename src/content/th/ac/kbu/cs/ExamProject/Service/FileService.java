package th.ac.kbu.cs.ExamProject.Service;

public interface FileService {
	public Boolean exists(String path);
	public Boolean makeDirectory(String path);
}
