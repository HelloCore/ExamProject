package th.ac.kbu.cs.ExamProject.Bean;

import java.util.ArrayList;
import java.util.List;

import th.ac.kbu.cs.ExamProject.Entity.Section;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class RegisterSectionBean {
	
	private Integer sectionYear;
	private Integer sectionSemester; 
	
	private List<Object[]> sections;

	public void addSection(Object[] section){
		if(BeanUtils.isNull(this.sections)){
			this.sections = new ArrayList<Object[]>();
		}
		this.sections.add(section);
	}

	public Integer getSectionYear() {
		return sectionYear;
	}

	public List<Object[]> getSections() {
		return sections;
	}

	public void setSections(List<Object[]> sections) {
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
