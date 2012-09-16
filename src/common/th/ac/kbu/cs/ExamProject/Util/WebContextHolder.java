package th.ac.kbu.cs.ExamProject.Util;

import javax.servlet.ServletContext;

public class WebContextHolder {

	private static ServletContext servletContext;

	public static ServletContext getServletContext() {
		return WebContextHolder.servletContext;
	}

	public static void setServletContext(final ServletContext servletContext) {
		WebContextHolder.servletContext = servletContext;
	}


}
