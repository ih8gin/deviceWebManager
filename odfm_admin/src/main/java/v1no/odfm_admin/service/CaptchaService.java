package v1no.odfm_admin.service;


public interface CaptchaService {
    /**
     * 发起邮箱验证请求
     * @param email 待验证邮箱
     */
    void registerEmail(String email);

    /**
     * 邮箱验证码核验
     * @param email 待验证邮箱、验证码
     */
    boolean verifyEmail(String email, String captcha);

    /**
     * 发起电话验证请求
     * @param phone 待验证电话
     */
    void registerPhone(String phone) throws Exception;

    /**
     * 电话验证码核验
     * @param phone 待验证电话、验证码
     */
    boolean verifyPhone(String phone, String captcha);
}
