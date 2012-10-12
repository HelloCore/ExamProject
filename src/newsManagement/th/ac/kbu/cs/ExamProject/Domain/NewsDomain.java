package th.ac.kbu.cs.ExamProject.Domain;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.CoreGrid.CoreGrid;
import th.ac.kbu.cs.ExamProject.Entity.News;
import th.ac.kbu.cs.ExamProject.Exception.CoreException;
import th.ac.kbu.cs.ExamProject.Exception.CoreExceptionMessage;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;
import th.ac.kbu.cs.ExamProject.Util.SecurityUtils;

@Configurable
public class NewsDomain extends NewsPrototype{

	@Autowired
	private BasicFinderService basicFinderService;
	
	@Autowired
	private BasicEntityService basicEntityService;
	
	private void validateAddData(){
		if(BeanUtils.isEmpty(this.getCourseId())
				|| BeanUtils.isEmpty(this.getNewsContent())
				|| BeanUtils.isEmpty(this.getNewsHeader())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public void addNews() {
		this.validateAddData();
		News news = new News();
		if(!this.getCourseId().equals(0L)){
			news.setCourseId(getCourseId());
		}
		news.setNewsContent(this.getNewsContent());
		news.setNewsHeader(this.getNewsHeader());
		news.setUpdateDate(new Date());
		news.setUsername(SecurityUtils.getUsername());
		news.setFlag(true);
		basicEntityService.save(news);
	}

	public CoreGrid<HashMap<String, Object>> search(
			NewsCoreGridManager gridManager) {
		return gridManager.search(this, SecurityUtils.getUsername());
	}

	private void validateDeleteData(){
		if(BeanUtils.isEmpty(this.getNewsId())){
			throw new CoreException(CoreExceptionMessage.PARAMETER_NOT_FOUND);
		}
	}
	
	public void deleteNews(NewsCoreGridManager gridManager) {
		this.validateDeleteData();
		gridManager.delete(this);
	}

}
