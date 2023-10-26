package v1no.odfm_device.service;

import v1no.odfm_common.domainModel.BO.DeviceBO;
import v1no.odfm_common.domainModel.QO.DeviceQO;

import java.util.List;

public interface DeviceService {
    /**
     * 创建设备信息
     * @param device 待存储设备信息
     * @return  写入数据库持久化的内容
     */
    DeviceBO create(DeviceBO device) throws Exception;

    /**
     * 更新设备信息
     * @param device 待更新设备信息
     * @return  更新后的设备信息
     */
    DeviceBO update(DeviceBO device) throws Exception;

    /**
     * 停用设备
     * @param device 待停用设备信息
     */
    DeviceBO cease(DeviceBO device) throws Exception;

    /**
     * 删除设备信息
     * @param device 待删除设备信息
     */
    void delete(DeviceQO device) throws Exception;

    /**
     * 查询设备详细信息
     * @param query 查询条件，单一对象查询，条件中需包含设备id
     * @return 设备详细信息与其产生的告警记录
     */
    DeviceBO getDetail(DeviceQO query) throws Exception;

    /**
     * 查询设备id信息
     * @param query 查询条件
     * @return 符合查询条件的设备
     */
    DeviceBO getProfile(DeviceQO query) throws Exception;

    /**
     * 查询设备列表
     * @param query 查询条件
     * @return 符合查询条件的设备列表
     */
    List<DeviceBO> getList(DeviceQO query) throws Exception;
}
