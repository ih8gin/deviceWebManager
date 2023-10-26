package v1no.odfm_admin.provider;

import v1no.odfm_common.domainModel.BO.CaptchaVerificationBO;
import v1no.odfm_common.result.Result;

public interface AdminVerifyProvider {
    /**
     * 发起邮箱验证请求
     * @param email 待验证邮箱
     */
    Result registerEmail(CaptchaVerificationBO email);

    /**
     * 邮箱验证码核验
     * @param email 待验证邮箱、验证码
     */
    Result verifyEmail(CaptchaVerificationBO email);

    /**
     * 发起电话验证请求
     * @param phone 待验证电话
     */
    Result registerPhone(CaptchaVerificationBO phone) throws Exception;

    /**
     * 电话验证码核验
     * @param phone 待验证电话、验证码
     */
    Result verifyPhone(CaptchaVerificationBO phone);


}
