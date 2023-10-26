package v1no.odfm_admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import v1no.odfm_common.domainModel.PO.AdminBusinessPO;
import v1no.odfm_common.domainModel.QO.AdminQO;

@Mapper
public interface AdminBusinessMapper {
    public void insert(AdminBusinessPO adminBusinessPO);
    public int update(AdminBusinessPO adminBusinessPO);
    public AdminBusinessPO get(AdminQO query);
    public int delete(AdminQO query);
}
