package v1no.odfm_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import v1no.odfm_common.exception.GlobelExceptionHandler;

@SpringBootApplication
@Import({GlobelExceptionHandler.class})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
