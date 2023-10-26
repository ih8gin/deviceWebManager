package v1no.odfm_common.exception;

public enum BizExceptionType {
    BAD_REQUEST(40000, "请求错误"),
    FORBIDDEN(40001, "无权操作"),
    INVALID_ARGS(40002,"参数校验不通过"),
    NO_EFFECT(40003,"请求无法生效"),
    INVALID_TOKEN(40004, "Token已过期");

    private final Integer code;
    private final String message;
    private BizExceptionType(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){return this.code;}
    public String getMessage(){return this.message;}
}
