package v1no.odfm_common.exception;

public class BizException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;

    public BizException(BizExceptionType bizExceptionType){
        this.code = bizExceptionType.getCode();
        this.message = bizExceptionType.getMessage();
    }

    public BizException(BizExceptionType bizExceptionType, String message){
        this.code = bizExceptionType.getCode();
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
