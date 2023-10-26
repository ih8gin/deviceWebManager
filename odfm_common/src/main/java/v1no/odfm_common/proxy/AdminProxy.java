package v1no.odfm_common.proxy;

import org.springframework.cloud.openfeign.SpringQueryMap;
import v1no.odfm_common.domainModel.BO.AdminBO;
import v1no.odfm_common.domainModel.QO.AdminQO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import v1no.odfm_common.result.Result;

@FeignClient(value = "adminservice")
public interface AdminProxy {
    @GetMapping ("/login/")
    Result login(AdminQO query);

    @PostMapping ("/")
    Result register(AdminBO admin);

    @GetMapping ("/")
    Result getProfile(@SpringQueryMap AdminQO adminQO);

    @GetMapping ("/detail/")
    Result getDetail(@SpringQueryMap AdminQO adminQO);

    @PutMapping ("/")
    Result update(AdminBO adminBO);

    @DeleteMapping("/")
    Result close(@SpringQueryMap AdminQO adminBO);
}
