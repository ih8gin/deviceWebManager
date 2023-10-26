package v1no.odfm_admin.service;

import v1no.odfm_common.domainModel.BO.AdminBO;
import v1no.odfm_common.domainModel.QO.AdminQO;

public interface AdminService {
    /**
     * 创建账户信息
     * @param admin 待存储的账户详细信息
     * @return  写入数据库持久化的内容
     */
    public AdminBO create(AdminBO admin);

    /**
     * 更新账户信息
     * @param admin 待更新账户详细信息
     * @return  更新后的账户信息
     */
    public AdminBO update(AdminBO admin);

    /**
     * 删除账户信息
     * @param admin 待删除设备信息
     */
    public void delete(AdminQO admin);

    /**
     * 获取账户基本信息
     * @param query 查询条件
     * @return 账户基本信息
     */
    public AdminBO getProfile(AdminQO query);

    /**
     * 获取账户详细信息
     * @param query 查询条件
     * @return 账户详细信息与所管理的设备信息
     */
    public AdminBO getDetail(AdminQO query);
}
