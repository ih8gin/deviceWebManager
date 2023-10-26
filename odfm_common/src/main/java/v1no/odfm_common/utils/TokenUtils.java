package v1no.odfm_common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TokenUtils {
//    @Value("${token.length}")
    static int length = 20;

//    @Value("${token.valid-time}")
    static int validTime = 1440;
    public static boolean verify(StringRedisTemplate redisTemplate, String token){
        if (Boolean.TRUE.equals(redisTemplate.hasKey(token))){
            redisTemplate.expire(token, validTime, TimeUnit.MINUTES);
            return true;
        }
        return false;
    };

    public static String applyToken(StringRedisTemplate redisTemplate, String id){
        String key = generateToken(length);
        String value = String.format("token for %s", id);
        redisTemplate.opsForValue().set(key, value, validTime, TimeUnit.MINUTES);
        return key;
    }

    private static String generateToken(int length){
        String range = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i){
            token.append(range.charAt(random.nextInt(range.length())));
        }
        return token.toString();
    }
}
