package v1no.odfm_admin.provider;

import org.springframework.validation.annotation.Validated;
import v1no.odfm_common.domainModel.BO.AdminBO;
import v1no.odfm_common.domainModel.QO.AdminQO;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.result.ResultType;
import v1no.odfm_common.validateGroup.insertValidation;

import javax.validation.Valid;

@Validated
public interface AdminProvider {
    /**
     * 登录
     * @param query 登录信息，包含username与password
     * @return 账户详细信息
     */
    Result login(AdminQO query);

    /**
     * 注册
     * @param admin 账户注册信息
     * @return 账户基本信息
     */
    @Validated(insertValidation.class)
    Result register(@Valid AdminBO admin);

    /**
     * 获取账户基本信息
     * @param query 查询条件
     * @return 账户基本信息
     */
    Result getProfile(AdminQO query);

    /**
     * 获取账户详细信息
     * @param query 查询条件
     * @return 账户详细信息与所管理的设备信息
     */
    Result getDetail(AdminQO query);

    /**
     * 更新账户信息
     * @param admin 待更新的账户信息
     * @return  更新后的账户信息
     */
    Result update(AdminBO admin);

    /**
     * 注销账户
     * @param admin 待注销账户信息
     */
    Result close(AdminQO admin);
}
