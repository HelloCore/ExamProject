package th.ac.kbu.cs.ExamProject.Listener;

import java.util.Date;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import th.ac.kbu.cs.ExamProject.Util.LoggingUtils;

/**
 *
 * @author twinp
 */
public class LoggingInterceptor implements MethodInterceptor {

    private final Logger log = Logger.getLogger(getClass());

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info(new Date());
            log.info("Beginning method: "
                    + methodInvocation.getMethod().getDeclaringClass() + "::"
                    + methodInvocation.getMethod().getName());

            log.info("Method parameters: "
                    + methodInvocation.getArguments().length);

            for (Object object : methodInvocation.getArguments()) {
                log.info(LoggingUtils.createToString(object));
            }
        }

        long startTime = System.currentTimeMillis();

        try {
            Object returnValue = methodInvocation.proceed();
            if (log.isInfoEnabled()) {
                log.debug("Return value of method: " + LoggingUtils.createToString(returnValue));
            }
            return returnValue;
        } finally {
            if (log.isInfoEnabled()) {
                log.info("Ending method: "
                        + methodInvocation.getMethod().getDeclaringClass()
                        + "::" + methodInvocation.getMethod().getName());
                log.info("Method invocation time: "
                        + (System.currentTimeMillis() - startTime) + " msecs.");
            }
        }
    }
}