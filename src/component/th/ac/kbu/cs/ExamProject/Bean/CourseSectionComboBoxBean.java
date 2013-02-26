package th.ac.kbu.cs.ExamProject.Bean;

import java.util.ArrayList;
import java.util.List;

import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

public class CourseSectionComboBoxBean<T> {
	private String courseCode;

	private List<T> sections;

	public void addSection(T section){
		if(BeanUtils.isNull(this.sections)){
			this.sections = new ArrayList<T>();
		}
		this.sections.add(section);
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public List<T> getSections() {
		return sections;
	}

	public void setSections(List<T> sections) {
		this.sections = sections;
	}
	
}
