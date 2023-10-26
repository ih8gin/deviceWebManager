package v1no.odfm_admin.service;

import v1no.odfm_common.domainModel.BO.AdminBO;
import v1no.odfm_common.domainModel.QO.AdminQO;

public interface AdminBusinessService {
    /**
     * 创建账户业务信息
     * @param admin 待存储的账户业务信息
     */
    public void create(AdminBO admin);

    /**
     * 更新账户业务信息
     * @param admin 待更新账户业务信息
     */
    public void update(AdminBO admin);

    /**
     * 获取账户业务信息
     * @param query 查询条件
     * @return 账户业务信息
     */
    public AdminBO get(AdminQO query);

    /**
     * 获取账户业务信息
     * @param query 查询条件
     */
    public void delete(AdminQO query);
}
