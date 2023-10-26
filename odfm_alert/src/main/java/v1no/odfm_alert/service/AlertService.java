package v1no.odfm_alert.service;

import v1no.odfm_common.domainModel.BO.AlertBO;
import v1no.odfm_common.domainModel.QO.AlertQO;

import java.util.List;

public interface AlertService {
    /**
     * 设备向管理系统提交一个告警
     * @param alert 告警信息
     * @return 写入MySQL持久化的内容
     */
    public AlertBO create(AlertBO alert) throws Exception;

    /**
     * 根据条件查询告警
     * @param query 查询条件
     * @return 符合查询条件的告警信息列表
     */
    public List<AlertBO> getList(AlertQO query) throws Exception;

    /**
     * 按条件删除告警
     * @param query 筛选条件
     */
    public void del(AlertQO query) throws Exception;
}
