package v1no.odfm_alert.service;

import v1no.odfm_common.domainModel.BO.AlertBO;
import v1no.odfm_common.domainModel.PO.AlertPO;
import v1no.odfm_common.domainModel.QO.AlertQO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.alibaba.fastjson.JSON;
import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class AlertServiceTest {
    @Resource
    private AlertService alertService;

    @Test
    void create(){
        AlertBO alert = new AlertBO();
        alert.setDeviceId(3L);
        alert.setType(2);
        alert.setTime(Instant.parse("2024-07-04T14:05:16.102Z"));
        try {
            alertService.create(alert);
        } catch (Exception e) { // ValidationException
            System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getList(){
        AlertQO query = new AlertQO();
//        query.setDeviceId(1L);
        List<AlertBO> alerts = null;
        try {
            alerts = alertService.getList(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(alerts);
    }
}
