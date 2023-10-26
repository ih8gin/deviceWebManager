package v1no.odfm_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import v1no.odfm_common.aop.paramsLog.ParamsLogAspect;
import v1no.odfm_common.exception.GlobelExceptionHandler;
import v1no.odfm_common.proxy.DeviceProxy;

@SpringBootApplication
@Import({ParamsLogAspect.class, GlobelExceptionHandler.class})
@EnableFeignClients(clients = {DeviceProxy.class})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}