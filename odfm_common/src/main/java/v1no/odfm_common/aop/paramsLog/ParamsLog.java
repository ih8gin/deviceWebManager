package v1no.odfm_common.aop.paramsLog;

import java.lang.annotation.*;

/**
 * 注解用于日志记录函数调用时的入参出参
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ParamsLog {
}
