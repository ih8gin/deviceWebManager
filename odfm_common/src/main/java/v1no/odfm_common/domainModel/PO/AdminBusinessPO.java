package v1no.odfm_common.domainModel.PO;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class AdminBusinessPO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 账户id
     */
    Long adminId;

    /**
     * 账户创建时间
     */
    Instant initTime;

    /**
     * 账户类型
     * 0-个人账户；1-企业账户
     */
    Integer accountType;

    /**
     * 企业/个人名称
     */
    String name;

    /**
     * 身份验证证明
     */
    String verification;

    /**
     * 邮寄地址
     */
    String address;

    /**
     * 应用领域
     */
    String field;
}
