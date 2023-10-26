package v1no.odfm_alert.provider.impl;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import v1no.odfm_alert.provider.AlertProvider;
import v1no.odfm_alert.service.AlertService;
import v1no.odfm_common.aop.paramsLog.ParamsLog;
import v1no.odfm_common.domainModel.BO.AlertBO;
import v1no.odfm_common.domainModel.BO.DeviceBO;
import v1no.odfm_common.domainModel.QO.AlertQO;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;
import v1no.odfm_common.proxy.DeviceProxy;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.result.ResultType;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class AlertProviderImpl implements AlertProvider {
    @Resource
    private AlertService alertService;

    @PostMapping("/")
    @ParamsLog
    @Override
    public Result submit(AlertBO alertBO) throws Exception{
        alertBO = alertService.create(alertBO);
        return new Result(ResultType.SUCCESS, alertBO);
    }

    @GetMapping("/")
    @ParamsLog
    @Override
    public Result getList(AlertQO query) throws Exception{
        List<AlertBO> alerts = alertService.getList(query);
        return new Result(ResultType.SUCCESS, alerts);
    }

    @DeleteMapping("/")
    @ParamsLog
    @Override
    public Result del(AlertQO query) throws Exception{
        if (query == null || query.getAdminId() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        alertService.del(query);
        return new Result(ResultType.SUCCESS);
    }
}
