package th.ac.kbu.cs.ExamProject.Util;

import java.lang.reflect.Method;

@Deprecated
public class MethodUtils {
	public static Method getMethod(final Class<?> clazz, final String methodName, final Class<?>... classes) {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, classes);
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
		return method;
	}
}

