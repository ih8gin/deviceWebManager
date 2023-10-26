package v1no.odfm_common.domainModel.QO;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class DeviceQO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 查询设备id
     */
    Long id;

    /**
     * 查询设备所属管理员账户id
     */
    Long adminId;

    /**
     * 查询设备硬件验证码
     */
    String verificationCode;

    /**
     * 查询设备类别
     */
    Integer[] type;

    /**
     * 查询设备激活起始时间
     */
    Instant activationTimeFrom;

    /**
     * 查询设备激活截止时间
     */
    Instant activationTimeTo;

    /**
     * 查询设备过期起始时间
     */
    Instant expirationTimeFrom;

    /**
     * 查询设备有效期截止时间
     */
    Instant expirationTimeTo;
}

