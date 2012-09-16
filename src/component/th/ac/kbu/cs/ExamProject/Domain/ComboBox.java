package th.ac.kbu.cs.ExamProject.Domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;

@Configurable
public class ComboBox{

	@Autowired
	protected BasicFinderService basicFinderService;
	
	private Boolean optionAll;

	public Boolean getOptionAll() {
		return optionAll;
	}

	public void setOptionAll(Boolean optionAll) {
		this.optionAll = optionAll;
	}

}
