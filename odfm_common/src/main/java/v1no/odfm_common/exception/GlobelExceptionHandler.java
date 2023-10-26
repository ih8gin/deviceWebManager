package v1no.odfm_common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.result.ResultType;

import javax.validation.ValidationException;

@RestControllerAdvice
@Slf4j
public class GlobelExceptionHandler {

    @ExceptionHandler({BizException.class, ValidationException.class})
    public Result handleNormalException(Exception e){
        Result result = new Result(ResultType.FAIL);
        result.setMessage(e.getMessage());
        return result;
    }

    @ExceptionHandler({Exception.class})
    public Result handleUnexpectedException(RuntimeException e){
        log.warn("Unexpected exception encountered: {}", e.getMessage());
        return new Result(ResultType.SYSTEM_ERROR);
    }
}
