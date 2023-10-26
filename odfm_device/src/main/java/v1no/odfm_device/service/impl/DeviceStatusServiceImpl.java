package v1no.odfm_device.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import v1no.odfm_common.domainModel.BO.DeviceBO;
import v1no.odfm_common.domainModel.QO.DeviceQO;
import v1no.odfm_device.service.DeviceService;
import v1no.odfm_device.service.DeviceStatusService;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DeviceStatusServiceImpl implements DeviceStatusService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private DeviceService deviceService;

    @Override
    public void heartbeat(String deviceVerificationCode, Long adminId) throws Exception {
        String key = String.format("DEVICE_HEARTBEAT_FOR_%s", adminId);
        redisTemplate.opsForZSet().add(key, deviceVerificationCode, System.currentTimeMillis());
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }

    @Override
    public int[] getSummary(Long adminId) throws Exception {
        int[] summary = new int[3];

        DeviceQO query = new DeviceQO();
        query.setAdminId(adminId);
        List<DeviceBO> deviceList = deviceService.getList(query);
        if (deviceList == null) return summary;
        summary[0] = deviceList.size();

        String runningDevice = String.format("DEVICE_HEARTBEAT_FOR_%s", adminId);
        long periodEnd = System.currentTimeMillis();
        long periodStart = periodEnd - 1000L*60*10; // 10min
//        log.debug("period start: " + periodStart);
//        log.debug("period end: " + periodEnd);
        Long runningDeviceCount = redisTemplate.opsForZSet().count(runningDevice, periodStart, periodEnd);
        if (runningDeviceCount != null)
            summary[1] = runningDeviceCount.intValue();
//            log.debug("running device: " + runningDeviceCount);
        String warningDevice = String.format("DEVICE_WARN_FOR_%s", adminId);
        periodStart = periodEnd - 1000L*60*10; // 10min
        Long warningDeviceCount = redisTemplate.opsForZSet().count(warningDevice, periodStart, periodEnd);
        if (warningDeviceCount != null)
            summary[2] = warningDeviceCount.intValue();
//            log.debug("warning device: " + warningDeviceCount);

        return summary;
    }

    @Override
    public void alert(String deviceVerificationCode, Long adminId) throws Exception {
        String key = String.format("DEVICE_WARN_FOR_%s", adminId);
        redisTemplate.opsForZSet().add(key, deviceVerificationCode, System.currentTimeMillis());
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }
}
