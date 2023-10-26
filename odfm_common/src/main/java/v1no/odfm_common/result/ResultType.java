package v1no.odfm_common.result;

public enum ResultType {
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    SYSTEM_ERROR(401, "异常");

    private final Integer code;
    private final String message;
    private ResultType(int code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }

    public boolean equals(Integer code){
        if (code == null)
            return false;
        return this.code.equals(code);
    }
}
