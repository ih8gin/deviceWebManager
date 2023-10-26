package v1no.odfm_device.provider;

import org.springframework.validation.annotation.Validated;
import v1no.odfm_common.domainModel.BO.DeviceBO;
import v1no.odfm_common.domainModel.QO.DeviceQO;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.validateGroup.insertValidation;

import javax.validation.Valid;

@Validated
public interface DeviceProvider {
    /**
     * 激活设备
     * @param device 待激活设备详细信息
     * @return  写入数据库持久化的内容
     */
    @Validated(insertValidation.class)
    public Result active(@Valid DeviceBO device) throws Exception;

    /**
     * 更新设备信息
     * @param device 待更新设备详细信息
     * @return  写入数据库持久化的内容
     */
    public Result update(DeviceBO device) throws Exception;

    /**
     * 停用设备
     * @param device 待停用设备信息
     */
    public Result cease(DeviceBO device) throws Exception;

    /**
     * 删除设备信息
     * @param device 待删除设备信息
     */
    public Result delete(DeviceQO device) throws Exception;

    /**
     * 查询设备id
     * @param query 查询条件，由硬件端自查时调用，条件中需要包含设备verificationCode
     * @return 设备id
     */
    public Result getId(DeviceQO query) throws Exception;

    /**
     * 查询设备详细信息
     * @param query 查询条件，单一对象查询，条件中需包含设备id
     * @return 设备详细信息与其产生的告警记录
     */
    public Result getDetail(DeviceQO query) throws Exception;

    /**
     * 查询设备列表
     * @param query 查询条件
     * @return 符合查询条件的设备列表
     */
    public Result getList(DeviceQO query) throws Exception;

    /**
     * 端侧设备启动时周期调用，更新设备运行状态
     * @param device 设备验证码
     */
    public Result heartbeat(DeviceBO device) throws Exception;

    /**
     * 查看设备运行状态摘要
     * @param query 管理账户id
     * @return 设备总数，运行中设备数量，告警设备数量
     */
    public Result getSummary(DeviceQO query) throws Exception;

    /**
     * 端侧设备产生告警时调用，更新设备运行状态
     * @param device 设备验证码，设备所属用户id
     */
    public Result alert(DeviceBO device) throws Exception;
}
