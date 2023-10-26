package v1no.odfm_alert.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import v1no.odfm_common.domainModel.BO.DeviceBO;
import v1no.odfm_common.domainModel.PO.AlertPO;
import v1no.odfm_common.domainModel.BO.AlertBO;
import v1no.odfm_common.domainModel.QO.AlertQO;
import org.springframework.stereotype.Service;
import v1no.odfm_alert.mapper.AlertMapper;
import v1no.odfm_alert.service.AlertService;
import v1no.odfm_common.domainModel.QO.DeviceQO;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;
import v1no.odfm_common.proxy.DeviceProxy;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.result.ResultType;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertServiceImpl implements AlertService {

    @Resource
    private AlertMapper alertMapper;

    @Resource
    private DeviceProxy deviceProxy;

    @Override
    public AlertBO create(AlertBO alert) throws Exception{
        AlertPO alertPO = new AlertPO();
        BeanUtils.copyProperties(alert, alertPO);
        DeviceQO deviceQO = new DeviceQO();
        deviceQO.setVerificationCode(alert.getDeviceVerificationCode());
        Result deviceResult = deviceProxy.getId(deviceQO);
        if (!ResultType.SUCCESS.equals(deviceResult.getStatusCode()))
            throw new BizException(BizExceptionType.BAD_REQUEST, String.valueOf(deviceResult.getData()));
        String data = JSONObject.toJSONString(deviceResult.getData());
        DeviceBO deviceBO = JSONObject.parseObject(data, DeviceBO.class);
        alertPO.setDeviceId(deviceBO.getId());
        alertPO.setAdminId(deviceBO.getAdminId());
        alertMapper.insert(alertPO);
        deviceProxy.alert(deviceBO);
        BeanUtils.copyProperties(alertPO, alert);
        return alert;
    }

    @Override
    public List<AlertBO> getList(AlertQO query) throws Exception{
        if (ObjectUtils.isEmpty(query.getId()) && ObjectUtils.isEmpty(query.getAdminId()) && ObjectUtils.isEmpty(query.getDeviceId())){
            throw new BizException(BizExceptionType.INVALID_ARGS);
        }
        List<AlertPO> alerts = alertMapper.getList(query);
        List<AlertBO> alertBOList = alerts.stream().map(alertPO -> {
            AlertBO alertBO = new AlertBO();
            BeanUtils.copyProperties(alertPO, alertBO);
            return alertBO;
        }).collect(Collectors.toList());
        return alertBOList;
    }

    @Override
    public void del(AlertQO query) throws Exception{
        int effectedColumns = alertMapper.delete(query);
        if (effectedColumns == 0)
            throw new BizException(BizExceptionType.NO_EFFECT);
    }
}
