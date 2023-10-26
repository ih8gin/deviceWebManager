package v1no.odfm_common.proxy;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import v1no.odfm_common.domainModel.BO.DeviceBO;
import v1no.odfm_common.domainModel.QO.DeviceQO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import v1no.odfm_common.result.Result;

@FeignClient(value = "deviceservice")
public interface DeviceProxy {
    @PostMapping ("/")
    Result activate(DeviceBO device);

    @PutMapping("/")
    Result update(DeviceBO device);

    @PutMapping("/cease/")
    Result cease(DeviceBO device);

    @DeleteMapping("/")
    Result delete(DeviceQO device);

    @GetMapping("/detail/")
    Result getDetail(@SpringQueryMap DeviceQO deviceQO);

    @GetMapping("/id/")
    Result getId(@SpringQueryMap DeviceQO deviceQO);

    @GetMapping ("/")
    Result getList(@SpringQueryMap DeviceQO deviceQO);

    @PutMapping("/heartbeat")
    Result heartbeat(DeviceBO device);

    @GetMapping("/summary")
    Result getSummary(@SpringQueryMap DeviceQO query);

    @PutMapping("/alert")
    Result alert(@SpringQueryMap DeviceBO device);
}
