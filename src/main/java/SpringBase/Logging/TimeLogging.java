package SpringBase.Logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeLogging {
    @Around("execution(* SpringBase.Zones.getZones(..))")
    public void logEntry(ProceedingJoinPoint pjp) throws Throwable {
        long timeMillis = System.currentTimeMillis();
        try {
            pjp.proceed();
        } catch(Exception e) {
            System.out.println("aaaaaaaaaaaaa");
        }
        System.out.println(System.currentTimeMillis() - timeMillis + "MS");
    }
}