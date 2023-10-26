package v1no.odfm_common.domainModel.BO;

import lombok.Data;
import v1no.odfm_common.validateGroup.insertValidation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
public class AdminBO implements Serializable {
    private static final long serialVersionUID = 1L;

// ------- 账户基本信息 -------------
    /**
     * 管理员账户唯一id
     */
    Long id;

    /**
     * 账户登录账户名
     */
    @NotNull(message = "登录账户名不能为空", groups = {insertValidation.class})
    String username;

    /**
     * 账户登录密码
     */
    @NotNull(message = "登录密码不能为空", groups = {insertValidation.class})
    String password;

    /**
     * 账户昵称
     */
    String nickname;

    /**
     * 账户联系电话
     */
    String phone;

    /**
     * 账户联系邮箱
     */
    @Email(message = "邮箱格式不合规", groups = {insertValidation.class})
    String email;

    /**
     * 账户备注说明
     */
    String note;

//-------- 设备信息 -----------
    /**
     * 账户管理的设备列表
     */
    List<DeviceBO> devices;

//-------- 登录验证信息 -----------
    /**
     * 登录验证token
     */
    String token;

//-------- 业务信息 -----------
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

//-------- 邮箱/电话验证信息 -----------
    /**
     * 邮箱/电话验证码
     */
    String captcha;
}
