package v1no.odfm_alert.mapper;

import v1no.odfm_common.domainModel.PO.AlertPO;
import v1no.odfm_common.domainModel.QO.AlertQO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlertMapper {
    public void insert(AlertPO alert);
    public List<AlertPO> getList(AlertQO query);

    public int delete(AlertQO query);
}
