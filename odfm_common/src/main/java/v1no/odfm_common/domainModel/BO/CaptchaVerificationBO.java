package v1no.odfm_common.domainModel.BO;

import lombok.Data;

import java.io.Serializable;

@Data
public class CaptchaVerificationBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 待验证邮箱
     */
    String email;

    /**
     * 待验证电话
     */
    String phone;

    /**
     * 验证码
     */
    String captcha;
}
