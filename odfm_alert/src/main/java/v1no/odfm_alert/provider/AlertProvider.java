package v1no.odfm_alert.provider;

import org.springframework.validation.annotation.Validated;
import v1no.odfm_common.domainModel.BO.AlertBO;
import v1no.odfm_common.domainModel.QO.AlertQO;
import v1no.odfm_common.result.Result;
import v1no.odfm_common.validateGroup.insertValidation;

import javax.validation.Valid;

@Validated
public interface AlertProvider {
    /**
     * 设备向管理系统提交一个告警
     * @param alert 告警信息
     * @return 写入数据库持久化的内容
     */
    @Validated(insertValidation.class)
    Result submit(@Valid AlertBO alert) throws Exception;

    /**
     * 根据条件查询告警
     * @param query 查询条件
     * @return 符合查询条件的告警信息列表
     */
    Result getList(AlertQO query) throws Exception;

    /**
     * 删除符合筛选条件的全部告警
     * @param query 筛选条件
     */
    Result del(AlertQO query) throws Exception;
}
