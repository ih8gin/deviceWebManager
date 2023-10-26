package v1no.odfm_admin.provider.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import v1no.odfm_admin.provider.AdminVerifyProvider;
import v1no.odfm_admin.service.CaptchaService;
import v1no.odfm_common.aop.paramsLog.ParamsLog;
import v1no.odfm_common.domainModel.BO.CaptchaVerificationBO;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.result.ResultType;

import javax.annotation.Resource;

@RestController
public class AdminVerifyProviderImpl implements AdminVerifyProvider {
    @Resource
    CaptchaService captchaService;

    @GetMapping("/captcha/request/email")
    @ParamsLog
    @Override
    public Result registerEmail(CaptchaVerificationBO email) {
        if (email == null || email.getEmail() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        captchaService.registerEmail(email.getEmail());
        return new Result(ResultType.SUCCESS);
    }

    @GetMapping("/captcha/verify/email")
    @ParamsLog
    @Override
    public Result verifyEmail(CaptchaVerificationBO email) {
        if (email == null || email.getEmail() == null || email.getCaptcha() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        if (!captchaService.verifyEmail(email.getEmail(), email.getCaptcha()))
            throw new BizException(BizExceptionType.INVALID_TOKEN, "验证码错误");
        return new Result(ResultType.SUCCESS);
    }

    @GetMapping("/captcha/request/phone")
    @ParamsLog
    @Override
    public Result registerPhone(CaptchaVerificationBO phone) throws Exception{
        if (phone == null || phone.getPhone() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        captchaService.registerPhone(phone.getPhone());
        return new Result(ResultType.SUCCESS);
    }

    @GetMapping("/captcha/verify/phone")
    @ParamsLog
    @Override
    public Result verifyPhone(CaptchaVerificationBO phone) {
        if (phone == null || phone.getPhone() == null || phone.getCaptcha() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        captchaService.verifyPhone(phone.getPhone(), phone.getCaptcha());
        return new Result(ResultType.SUCCESS);
    }
}
