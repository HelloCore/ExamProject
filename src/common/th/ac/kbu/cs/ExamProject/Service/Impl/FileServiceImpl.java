package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.io.File;
import org.springframework.stereotype.Service;
import th.ac.kbu.cs.ExamProject.Service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public Boolean exists(String path) {
		File file = new File(path);
		return file.exists();
	}

	@Override
	public Boolean makeDirectory(String path) {
		File file = new File(path);
		return file.mkdirs();
	}
	
}
