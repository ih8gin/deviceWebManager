package v1no.odfm_admin.provider;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest()
public class AdminProviderTest {
    @Resource
    RedisTemplate redisTemplate;


}
