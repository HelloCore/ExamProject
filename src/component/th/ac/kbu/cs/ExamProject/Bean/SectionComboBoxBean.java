package th.ac.kbu.cs.ExamProject.Bean;

import java.util.ArrayList;
import java.util.List;

import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class SectionComboBoxBean<T> {
	
	private Integer sectionYear;
	private Integer sectionSemester; 
	
	private List<T> sections;

	public void addSection(T section){
		if(BeanUtils.isNull(this.sections)){
			this.sections = new ArrayList<T>();
		}
		this.sections.add(section);
	}

	public Integer getSectionYear() {
		return sectionYear;
	}

	public List<T> getSections() {
		return sections;
	}

	public void setSections(List<T> sections) {
		this.sections = sections;
	}

	public void setSectionYear(Integer sectionYear) {
		this.sectionYear = sectionYear;
	}

	public Integer getSectionSemester() {
		return sectionSemester;
	}

	public void setSectionSemester(Integer sectionSemester) {
		this.sectionSemester = sectionSemester;
	}
	
	
}
