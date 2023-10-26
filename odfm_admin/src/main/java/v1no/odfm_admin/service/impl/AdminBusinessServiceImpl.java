package v1no.odfm_admin.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import v1no.odfm_admin.mapper.AdminBusinessMapper;
import v1no.odfm_admin.service.AdminBusinessService;
import v1no.odfm_common.domainModel.BO.AdminBO;
import v1no.odfm_common.domainModel.PO.AdminBusinessPO;
import v1no.odfm_common.domainModel.QO.AdminQO;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;

import javax.annotation.Resource;

@Service
public class AdminBusinessServiceImpl implements AdminBusinessService {
    @Resource
    AdminBusinessMapper adminBusinessMapper;

    @Override
    public void create(AdminBO admin) {
        AdminBusinessPO adminBusinessPO = new AdminBusinessPO();
        BeanUtils.copyProperties(admin, adminBusinessPO);
        if (adminBusinessPO.getAdminId() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        adminBusinessMapper.insert(adminBusinessPO);
    }

    @Override
    public void update(AdminBO admin) {
        AdminBusinessPO adminBusinessPO = new AdminBusinessPO();
        BeanUtils.copyProperties(admin, adminBusinessPO);
        if (adminBusinessPO.getAdminId() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        int effectedColumns = adminBusinessMapper.update(adminBusinessPO);
        if (effectedColumns != 1)
            throw new BizException(BizExceptionType.FORBIDDEN);
    }

    @Override
    public AdminBO get(AdminQO query) {
        if (query.getId() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        AdminBusinessPO adminBusinessPO = adminBusinessMapper.get(query);
        AdminBO adminBO = new AdminBO();
        BeanUtils.copyProperties(adminBusinessPO, adminBO);
        return adminBO;
    }

    @Override
    public void delete(AdminQO query) {
        if (query.getId() == null)
            throw new BizException(BizExceptionType.INVALID_ARGS);
        int effectedColumns = adminBusinessMapper.delete(query);
        if (effectedColumns > 1)
            throw new BizException(BizExceptionType.BAD_REQUEST);
        if (effectedColumns == 0)
            throw new BizException(BizExceptionType.NO_EFFECT);
    }
}
