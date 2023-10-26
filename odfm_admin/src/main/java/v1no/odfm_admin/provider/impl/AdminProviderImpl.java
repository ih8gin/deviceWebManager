package v1no.odfm_admin.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import v1no.odfm_admin.provider.AdminProvider;
import v1no.odfm_admin.service.AdminService;
import v1no.odfm_admin.service.CaptchaService;
import v1no.odfm_common.aop.paramsLog.ParamsLog;
import v1no.odfm_common.domainModel.BO.AdminBO;
import v1no.odfm_common.domainModel.QO.AdminQO;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.result.ResultType;
import v1no.odfm_common.utils.TokenUtils;

import javax.annotation.Resource;

@RestController
public class AdminProviderImpl implements AdminProvider {
    @Resource
    private AdminService adminService;

    @Resource
    private CaptchaService captchaService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login/")
    @ParamsLog
    @Override
    public Result login(AdminQO query) {
        AdminBO adminBO = adminService.getProfile(query);
        adminBO.setToken(TokenUtils.applyToken(redisTemplate, String.format("user_%s", adminBO.getId())));
        return new Result(ResultType.SUCCESS, adminBO);
    }

    @PostMapping("/register")
    @ParamsLog
    @Override
    public Result register(AdminBO admin) {
        if (admin == null || (admin.getEmail() == null && admin.getPhone() == null) || admin.getCaptcha() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        if (admin.getEmail() != null && !admin.getEmail().isEmpty() && !captchaService.verifyEmail(admin.getEmail(), admin.getCaptcha()))
            throw new BizException(BizExceptionType.INVALID_TOKEN, "邮箱验证码错误");
        if (admin.getPhone() != null && !admin.getPhone().isEmpty() && !captchaService.verifyEmail(admin.getPhone(), admin.getCaptcha()))
            throw new BizException(BizExceptionType.INVALID_TOKEN, "短信验证码错误");
        AdminBO adminBO = adminService.create(admin);
        return new Result(ResultType.SUCCESS, adminBO);
    }

    @GetMapping("/")
    @ParamsLog
    @Override
    public Result getProfile(AdminQO query) {
        AdminBO adminBO = adminService.getProfile(query);
        return new Result(ResultType.SUCCESS, adminBO);
    }

    @GetMapping("/detail")
    @ParamsLog
    @Override
    public Result getDetail(AdminQO query) {
        AdminBO adminBO = adminService.getDetail(query);
        return new Result(ResultType.SUCCESS, adminBO);
    }

    @PutMapping("/")
    @ParamsLog
    @Override
    public Result update(AdminBO admin) {
        if (admin == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        if ((admin.getEmail() != null || admin.getPhone() != null) && admin.getCaptcha() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        if (admin.getEmail() != null && !admin.getEmail().isEmpty() && !captchaService.verifyEmail(admin.getEmail(), admin.getCaptcha()))
            throw new BizException(BizExceptionType.INVALID_TOKEN, "邮箱验证码错误");
        if (admin.getPhone() != null && !admin.getPhone().isEmpty() && !captchaService.verifyPhone(admin.getPhone(), admin.getCaptcha()))
            throw new BizException(BizExceptionType.INVALID_TOKEN, "短信验证码错误");
        AdminBO adminBO = adminService.update(admin);
        return new Result(ResultType.SUCCESS, adminBO);
    }

    @DeleteMapping("/")
    @ParamsLog
    @Override
    public Result close(AdminQO admin) {
        adminService.delete(admin);
        return new Result(ResultType.SUCCESS);
    }
}
