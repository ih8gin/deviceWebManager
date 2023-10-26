package v1no.odfm_device.service.impl;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import v1no.odfm_common.domainModel.BO.AlertBO;
import v1no.odfm_common.domainModel.BO.DeviceBO;
import v1no.odfm_common.domainModel.PO.DevicePO;
import v1no.odfm_common.domainModel.QO.AlertQO;
import v1no.odfm_common.domainModel.QO.DeviceQO;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;
import v1no.odfm_common.proxy.AlertProxy;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.result.ResultType;
import v1no.odfm_device.mapper.DeviceMapper;
import v1no.odfm_device.service.DeviceService;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    DeviceMapper deviceMapper;

    @Resource
    AlertProxy alertProxy;

    @Override
    public DeviceBO create(DeviceBO device) throws Exception {
        DevicePO devicePO = new DevicePO();
        BeanUtils.copyProperties(device, devicePO);
        deviceMapper.insert(devicePO);
        BeanUtils.copyProperties(devicePO, device);
        return device;
    }

    @Override
    public DeviceBO update(DeviceBO device) throws Exception {
        if (device.getId() == null && StringUtils.isBlank(device.getVerificationCode()))
            throw new BizException(BizExceptionType.INVALID_ARGS, "设备id或设备硬件识别码不能均为空"); //TODO 将这句描述放进枚举类里
        DevicePO devicePO = new DevicePO();
        BeanUtils.copyProperties(device, devicePO);
        int effectedColumns = deviceMapper.update(devicePO);
        if (effectedColumns != 1)
            throw new BizException(BizExceptionType.FORBIDDEN);
        return device;
    }

    @Override
    public DeviceBO cease(DeviceBO device) throws Exception {
        device.setExpirationTime(Instant.now());
        DeviceBO deviceBO = update(device);
        return deviceBO;
    }

    @Override
    public void delete(DeviceQO device) throws Exception {
        if (device.getId() == null && StringUtils.isBlank(device.getVerificationCode()))
            throw new BizException(BizExceptionType.INVALID_ARGS);
        int effectedColumns = deviceMapper.delete(device);
        if (effectedColumns > 1)
            throw new BizException(BizExceptionType.BAD_REQUEST);
        if (effectedColumns == 0)
            throw new BizException(BizExceptionType.NO_EFFECT);
    }

    @Override
    public DeviceBO getDetail(DeviceQO query) throws Exception {
        if (query.getId() == null && StringUtils.isBlank(query.getVerificationCode()))
            throw new BizException(BizExceptionType.INVALID_ARGS);
        List<DevicePO> deviceList = deviceMapper.getList(query);
        if (deviceList == null || deviceList.size() != 1)
            throw new BizException(BizExceptionType.BAD_REQUEST);
        DeviceBO deviceBO = new DeviceBO();
        BeanUtils.copyProperties(deviceList.get(0), deviceBO);
        AlertQO alertQuery = new AlertQO();
        alertQuery.setDeviceId(new Long[]{deviceBO.getId()});
        Result alertsResult = alertProxy.getList(alertQuery);
        if (!ResultType.SUCCESS.equals(alertsResult.getStatusCode()))
            throw new BizException(BizExceptionType.BAD_REQUEST, String.valueOf(alertsResult.getMessage()));
        String data = JSONArray.toJSONString(alertsResult.getData());
        deviceBO.setAlerts(JSONArray.parseArray(data, AlertBO.class));
        return deviceBO;
    }

    @Override
    public DeviceBO getProfile(DeviceQO query) throws Exception {
        if (query.getId() == null && StringUtils.isBlank(query.getVerificationCode()))
            throw new BizException(BizExceptionType.INVALID_ARGS);
        DevicePO deviceProfile = deviceMapper.getProfile(query);
        DeviceBO deviceBO = new DeviceBO();
        BeanUtils.copyProperties(deviceProfile, deviceBO);
        return deviceBO;
    }
    @Override
    public List<DeviceBO> getList(DeviceQO query) throws Exception {
        if (query.getId() == null && StringUtils.isBlank(query.getVerificationCode()) && query.getAdminId() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        List<DevicePO> deviceList = deviceMapper.getList(query);
        List<DeviceBO> deviceBOList = deviceList.stream().map(devicePO -> {
            DeviceBO deviceBO = new DeviceBO();
            BeanUtils.copyProperties(devicePO, deviceBO);
            return deviceBO;
        }).collect(Collectors.toList());
        return deviceBOList;
    }
}
