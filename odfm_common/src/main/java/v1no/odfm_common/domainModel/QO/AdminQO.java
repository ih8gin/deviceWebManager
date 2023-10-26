package v1no.odfm_common.domainModel.QO;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminQO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 查询管理员账户id
     */
    Long id;

    /**
     * 查询管理员账户登录用户名
     */
    String username;

    /**
     * 查询管理员账户登录密码
     */
    String password;

    /**
     * 查询管理员账户昵称
     */
    String nickname;

    /**
     * 查询联系电话
     */
    String phone;

    /**
     * 查询联系邮箱
     */
    String email;
}
