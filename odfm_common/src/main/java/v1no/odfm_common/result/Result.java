package v1no.odfm_common.result;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    Integer statusCode;

    Object data;

    String message;

    public Result(){}

    public Result(@NonNull ResultType resultType, Object data){
        this.statusCode = resultType.getCode();
        this.message = resultType.getMessage();
        this.data = data;
    }

    public Result(@NonNull ResultType resultType){
        this.statusCode = resultType.getCode();
        this.message = resultType.getMessage();
    }

}
