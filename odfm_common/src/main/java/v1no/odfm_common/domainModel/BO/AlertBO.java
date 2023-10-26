package v1no.odfm_common.domainModel.BO;

import lombok.Data;
import v1no.odfm_common.validateGroup.insertValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.Instant;

@Data
public class AlertBO implements Serializable {
    private static final long serialVersionUID = 1L;

//-------- 告警信息列表 ----------------
    /**
     * 告警唯一标识
     */
    Long id;

    /**
     * 告警来源设备id
     */
//    @NotNull(message = "来源设备id不能为空", groups = {insertValidation.class})
    Long deviceId;

    /**
     * 设备标识码
     */
    @NotNull(message = "来源设备标识码不能为空", groups = {insertValidation.class})
    String deviceVerificationCode;

    /**
     * 告警来源设备id
     */
    Long adminId;

    /**
     * 来源用户信息
     */
    String userInfo;

    /**
     * 来源ip地址
     */
    String ip;

    /**
     * 告警产生原因类型
     */
    @NotNull(message = "产生原因类型不能为空", groups = {insertValidation.class})
    Integer type;

    /**
     * 告警产生时间
     */
    @NotNull(message = "产生时间不能为空", groups = {insertValidation.class})
//    @PastOrPresent(message = "产生时间不能超过当前时间", groups = {insertValidation.class})
    Instant time;
}
