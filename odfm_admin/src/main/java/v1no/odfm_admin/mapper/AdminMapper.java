package v1no.odfm_admin.mapper;

import v1no.odfm_common.domainModel.PO.AdminPO;
import v1no.odfm_common.domainModel.QO.AdminQO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    public void insert(AdminPO query);
    public int update(AdminPO query);
    public AdminPO getProfile(AdminQO query);
    public AdminPO getDetail(AdminQO query);
    public int delete(AdminQO query);
}