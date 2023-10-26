package v1no.odfm_common.domainModel.PO;

import lombok.Data;
import v1no.odfm_common.validateGroup.insertValidation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AdminPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 管理员账户唯一id
     */
    Long id;

    /**
     * 登录账户名
     */
    @NotNull(message = "登录账户名不能为空", groups = {insertValidation.class})
    String username;

    /**
     * 登录密码
     */
    @NotNull(message = "登录密码不能为空", groups = {insertValidation.class})
    String password;

    /**
     * 账户昵称
     */
    String nickname;

    /**
     * 联系电话
     */
    String phone;

    /**
     * 联系邮箱
     */
    @Email(message = "邮箱格式不合规", groups = {insertValidation.class})
    String email;

    /**
     * 备注说明
     */
    String note;
}

