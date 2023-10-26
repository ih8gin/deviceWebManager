package v1no.odfm_gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;
import v1no.odfm_common.utils.TokenUtils;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;


@Slf4j
@Component
public class TokenVerifyGlobalFilter implements GlobalFilter, Ordered{
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        URI uri = request.getURI();
        //String path = request.getPath().value();
        String path = request.getPath().pathWithinApplication().value();//打印请求路径
        //cors
        HttpHeaders headers = request.getHeaders();
        log.info("Verifying token ...");
        log.info(headers.toString());
        if (!path.contains("login") && !path.contains("heartbeat") && !path.contains("alert") && !path.contains("captcha") && !path.contains("register")){
            List<String> token = headers.get(HttpHeaders.AUTHORIZATION);
            if (token == null || token.size() == 0 || !TokenUtils.verify(redisTemplate, token.get(0))) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            log.info(String.format("Token:{ %s }, is verified", token.get(0)));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
