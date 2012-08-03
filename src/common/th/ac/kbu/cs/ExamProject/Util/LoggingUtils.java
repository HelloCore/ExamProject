package th.ac.kbu.cs.ExamProject.Util;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.reflect.Method;

/**
 *
 * @author twinp
 */
public class LoggingUtils {
    public static String createToString(Object object) {
        StringBuilder result = new StringBuilder();
        if (object != null) {

            result.append(object.getClass().getName());
            result.append(" { ");

            Method[] methods = object.getClass().getMethods();

            for (Method method : methods) {

                if (method.getName().startsWith("get")
                        && method.getParameterTypes().length == 0) {

                    String propertyNameWithoutFirstCharacter = method.getName().substring(4);
                    String propertyNamefirstCharacter = method.getName().substring(3, 4).toLowerCase();
                    String propertyName = propertyNamefirstCharacter
                            + propertyNameWithoutFirstCharacter;

                    Object propertyValue;

                    try {
                        propertyValue = method.invoke(object);
                    } catch (Exception e) {
                        propertyValue = e.getMessage();
                    }
                    result.append(" (");
                    result.append(propertyName);
                    result.append(": ");
                    result.append(propertyValue);
                    result.append(") ");
                }
            }
            result.append("}");
        } else {
            return "null";
        }
        return result.toString();
    }
}