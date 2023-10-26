package v1no.odfm_common.proxy;

import org.springframework.cloud.openfeign.SpringQueryMap;
import v1no.odfm_common.domainModel.BO.AlertBO;
import v1no.odfm_common.domainModel.QO.AlertQO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import v1no.odfm_common.result.Result;


@FeignClient(value = "alertservice")
public interface AlertProxy {
    @PostMapping ("/")
    Result submit(AlertBO alert);

    @GetMapping ("/")
    Result getList(@SpringQueryMap AlertQO alertQO);
}
