package v1no.odfm_common.aop.paramsLog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ParamsLogAspect {

    @Pointcut("@annotation(v1no.odfm_common.aop.paramsLog.ParamsLog)")
    public void paramsLog(){};

    @Around("paramsLog()")
    public Object doAroud(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("Request Args: {}", joinPoint.getArgs());
        Object result = joinPoint.proceed();
        log.info("Returning Args: {}", result);
        return result;
    }

}
