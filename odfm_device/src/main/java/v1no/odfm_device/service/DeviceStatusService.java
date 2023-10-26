package v1no.odfm_device.service;

import v1no.odfm_common.domainModel.BO.DeviceBO;

import java.util.List;

public interface DeviceStatusService {
    /**
     * 心跳程序，端侧设备周期调用
     * @param deviceVerificationCode 硬件设备识别码
     */
    void heartbeat(String deviceVerificationCode, Long adminId) throws Exception;

    /**
     * 获取管理员账户名下设备运行状态摘要
     * @param adminId
     * @return index_0-设备总数，index_1-运行中设备数量，index_2-告警设备数量
     */
    int[] getSummary(Long adminId) throws Exception;

    void alert(String deviceVerificationCode, Long adminId) throws Exception;
}
