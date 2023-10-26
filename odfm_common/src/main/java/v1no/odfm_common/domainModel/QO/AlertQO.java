package v1no.odfm_common.domainModel.QO;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class AlertQO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 查询告警id
     */
    Long id;

    /**
     * 查询告警所属设备管理员id
     */
    Long adminId;

    /**
     * 查询告警所属设备id
     */
    Long[] deviceId;

    /**
     * 查询告警类别
     */
    Integer[] type;

    /**
     * 查询告警起始时间区间
     */
    Instant timeFrom;

    /**
     * 查询告警截止时间区间
     */
    Instant timeTo;
}

