package v1no.odfm_common.domainModel.PO;

import lombok.Data;
import v1no.odfm_common.validateGroup.insertValidation;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.Instant;

@Data
public class DevicePO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 设备唯一id
     */
    Long id;

    /**
     * 设备所属管理员账号id
     */
    @NotNull(message = "设备所属管理员id不能为空", groups = {insertValidation.class})
    Long adminId;

    /**
     * 设备验证码，由设备硬件资源产生
     */
    @NotNull(message = "设备硬件识别码不能为空", groups = {insertValidation.class})
    String verificationCode;

    /**
     * 设备类型
     */
    @NotNull(message = "设备类型不能为空", groups = {insertValidation.class})
    Integer type;

    /**
     * 设备激活时间
     */
//    @NotNull(message = "激活时间不能为空", groups = {insertValidation.class})
    @PastOrPresent(message = "激活时间不能超过当前时间", groups = {insertValidation.class})
    Instant activationTime;

    /**
     * 设备有效截止时间
     */
//    @NotNull(message = "到期时间不能为空", groups = {insertValidation.class})
    @Future(message = "到期时间不能早于当前时间", groups = {insertValidation.class})
    Instant expirationTime;

    /**
     * 设备备注说明
     */
    String note;
}

