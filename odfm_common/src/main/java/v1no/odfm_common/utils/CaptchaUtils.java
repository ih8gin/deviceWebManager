package v1no.odfm_common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailSender;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CaptchaUtils {
//    @Value("${captcha.length}")
    static int captchaLength = 6;

//    @Value("${captcha.valid-time}")
    static int captchaValidTime = 5;

    public static String getCaptcha(StringRedisTemplate redisTemplate, String key){
        String captcha = generateCaptcha(captchaLength);
        redisTemplate.opsForValue().set(key, captcha, captchaValidTime, TimeUnit.MINUTES);
        return  captcha;
    }

    public static boolean verifyCaptcha(StringRedisTemplate redisTemplate, String key, String captcha){
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))){
            return captcha.equals(redisTemplate.opsForValue().get(key));
        }
        return false;
    }

    private static String generateCaptcha(int length){
//        String range = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String range = "0123456789";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i){
            token.append(range.charAt(random.nextInt(range.length())));
        }
        return token.toString();
    }
}
