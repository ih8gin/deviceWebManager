package v1no.odfm_admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import v1no.odfm_admin.mapper.AdminBusinessMapper;
import v1no.odfm_admin.mapper.AdminMapper;
import v1no.odfm_common.domainModel.BO.AdminBO;
import v1no.odfm_common.domainModel.BO.DeviceBO;
import v1no.odfm_common.domainModel.PO.AdminBusinessPO;
import v1no.odfm_common.domainModel.PO.AdminPO;
import v1no.odfm_common.domainModel.QO.AdminQO;
import v1no.odfm_admin.service.AdminService;
import v1no.odfm_common.domainModel.QO.DeviceQO;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;
import v1no.odfm_common.proxy.DeviceProxy;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.result.ResultType;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;

    @Resource
    AdminBusinessMapper adminBusinessMapper;

    @Resource
    DeviceProxy deviceProxy;

    @Override
    @Transactional
    public AdminBO create(AdminBO admin) {
        AdminPO adminPO = new AdminPO();
        BeanUtils.copyProperties(admin, adminPO);
        if (admin.getPhone() != null && !admin.getPhone().isEmpty()) adminPO.setUsername(admin.getPhone());
        if (admin.getEmail() != null && !admin.getEmail().isEmpty()) adminPO.setUsername(admin.getEmail());
        adminPO.setNickname(adminPO.getUsername());
        adminMapper.insert(adminPO);
        AdminBusinessPO adminBusinessPO = new AdminBusinessPO();
        adminBusinessPO.setAdminId(adminPO.getId());
        adminBusinessMapper.insert(adminBusinessPO);
        return admin;
    }

    @Override
    @Transactional
    public AdminBO update(AdminBO admin) {
        int effectedColumns;
        if(admin.getPassword() != null || admin.getPhone() != null || admin.getEmail() != null
            || admin.getNickname() != null || admin.getNote() != null) {
            AdminPO adminPO = new AdminPO();
            BeanUtils.copyProperties(admin, adminPO);
            effectedColumns = adminMapper.update(adminPO);
            if (effectedColumns != 1)
                throw new BizException(BizExceptionType.FORBIDDEN);
        }
        if(admin.getAccountType() != null || admin.getName() != null || admin.getAddress() != null
                || admin.getVerification() != null || admin.getField() != null){
            AdminBusinessPO adminBusinessPO = new AdminBusinessPO();
            BeanUtils.copyProperties(admin, adminBusinessPO);
            adminBusinessPO.setAdminId(admin.getId());
            effectedColumns = adminBusinessMapper.update(adminBusinessPO);
            if (effectedColumns != 1)
                throw new BizException(BizExceptionType.FORBIDDEN);
        }

        return admin;
    }

    @Override
    public void delete(AdminQO admin) {
        if (admin.getId() == null || StringUtils.isBlank(admin.getUsername()) || StringUtils.isBlank(admin.getPassword()))
            throw new BizException(BizExceptionType.INVALID_ARGS);
        int effectedColumns = adminMapper.delete(admin);
        // TODO 删除账户管理的设备
        if (effectedColumns > 1)
            throw new BizException(BizExceptionType.BAD_REQUEST);
        if (effectedColumns == 0)
            throw new BizException(BizExceptionType.NO_EFFECT);
        adminBusinessMapper.delete(admin);
    }

    @Override
    public AdminBO getProfile(AdminQO query) {
        if (query.getId() == null && (StringUtils.isBlank(query.getUsername()) || StringUtils.isBlank(query.getPassword())))
            throw new BizException(BizExceptionType.INVALID_ARGS);
        AdminPO adminProfile = adminMapper.getProfile(query);
        AdminBO adminBO = new AdminBO();
        BeanUtils.copyProperties(adminProfile, adminBO);
        return adminBO;
    }

    @Override
    public AdminBO getDetail(AdminQO query) {
        if (query.getId() == null && (StringUtils.isBlank(query.getUsername()) || StringUtils.isBlank(query.getPassword())))
            throw new BizException(BizExceptionType.INVALID_ARGS);
        AdminPO admin = adminMapper.getDetail(query);
        if (admin == null)
            throw new BizException(BizExceptionType.FORBIDDEN);
        AdminBO adminBO = new AdminBO();
        BeanUtils.copyProperties(admin, adminBO);
        query.setId(adminBO.getId());
        AdminBusinessPO adminBusinessPO = adminBusinessMapper.get(query);
        BeanUtils.copyProperties(adminBusinessPO, adminBO);
        return adminBO;
    }
}
