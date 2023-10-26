package v1no.odfm_device.provider.impl;

import org.springframework.web.bind.annotation.*;
import v1no.odfm_common.aop.paramsLog.ParamsLog;
import v1no.odfm_common.domainModel.BO.DeviceBO;
import v1no.odfm_common.domainModel.QO.DeviceQO;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.result.ResultType;
import v1no.odfm_device.provider.DeviceProvider;
import v1no.odfm_device.service.DeviceService;
import v1no.odfm_device.service.DeviceStatusService;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DeviceProviderImpl implements DeviceProvider {
    @Resource
    private DeviceService deviceService;

    @Resource
    private DeviceStatusService deviceStatusService;

    @PostMapping("/")
    @ParamsLog
    @Override
    public Result active(DeviceBO device) throws Exception {
        device = deviceService.create(device);
        return new Result(ResultType.SUCCESS, device);
    }

    @PutMapping("/")
    @ParamsLog
    @Override
    public Result update(DeviceBO device) throws Exception {
        device = deviceService.update(device);
        return new Result(ResultType.SUCCESS, device);
    }

    @PutMapping("/cease/")
    @ParamsLog
    @Override
    public Result cease(DeviceBO device) throws Exception {
        DeviceBO deviceBO = deviceService.cease(device);
        return new Result(ResultType.SUCCESS, deviceBO);
    }

    @DeleteMapping("/")
    @ParamsLog
    @Override
    public Result delete(DeviceQO device) throws Exception {
        deviceService.delete(device);
        return new Result(ResultType.SUCCESS);
    }

    @GetMapping("/id/")
    @ParamsLog
    @Override
    public Result getId(DeviceQO query) throws Exception {
        DeviceBO device = deviceService.getProfile(query);
        return new Result(ResultType.SUCCESS, device);
    }

    @GetMapping("/detail/")
    @ParamsLog
    @Override
    public Result getDetail(DeviceQO query) throws Exception {
        DeviceBO device = deviceService.getDetail(query);
        return new Result(ResultType.SUCCESS, device);
    }

    @GetMapping("/")
    @ParamsLog
    @Override
    public Result getList(DeviceQO query) throws Exception {
        List<DeviceBO> devices = deviceService.getList(query);
        return new Result(ResultType.SUCCESS, devices);
    }

    @PutMapping("/heartbeat")
    @ParamsLog
    @Override
    public Result heartbeat(DeviceBO device) throws Exception {
        if (device == null || device.getVerificationCode() == null) throw new BizException(BizExceptionType.INVALID_ARGS);
        String verificationCode = device.getVerificationCode();
        Long adminId = null;
        if (device.getAdminId() == null){
            DeviceQO query = new DeviceQO();
            query.setVerificationCode(verificationCode);
            DeviceBO deviceProfile = deviceService.getProfile(query);
            if (deviceProfile == null) throw new BizException(BizExceptionType.BAD_REQUEST);
            adminId = deviceProfile.getAdminId();
        }
        deviceStatusService.heartbeat(verificationCode, adminId);
        return new Result(ResultType.SUCCESS);
    }

    @GetMapping("/summary")
    @ParamsLog
    @Override
    public Result getSummary(DeviceQO query) throws Exception {
        if (query == null || query.getAdminId() == null) throw new BizException(BizExceptionType.INVALID_ARGS);
        int[] summary = deviceStatusService.getSummary(query.getAdminId());
        return new Result(ResultType.SUCCESS, summary);
    }

    @PutMapping("/alert")
    @ParamsLog
    @Override
    public Result alert(DeviceBO device) throws Exception {
        if (device == null || device.getVerificationCode() == null || device.getAdminId() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        deviceStatusService.alert(device.getVerificationCode(), device.getAdminId());
        return new Result(ResultType.SUCCESS);
    }
}
