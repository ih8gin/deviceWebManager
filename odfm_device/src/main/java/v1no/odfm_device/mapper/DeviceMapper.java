package v1no.odfm_device.mapper;

import org.apache.ibatis.annotations.Mapper;
import v1no.odfm_common.domainModel.PO.DevicePO;
import v1no.odfm_common.domainModel.QO.DeviceQO;

import java.util.List;

@Mapper
public interface DeviceMapper {
    public void insert(DevicePO device);
    public int update(DevicePO device);
    public int delete(DeviceQO device);
    public DevicePO getProfile(DeviceQO query);
    public List<DevicePO> getList(DeviceQO query);
}
