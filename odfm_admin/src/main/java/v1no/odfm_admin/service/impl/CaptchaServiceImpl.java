package v1no.odfm_admin.service.impl;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import v1no.odfm_admin.service.CaptchaService;
import v1no.odfm_common.exception.BizException;
import v1no.odfm_common.exception.BizExceptionType;
import v1no.odfm_common.utils.CaptchaUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    JavaMailSender javaMailSender;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Value("${aliyun.accesskey-id}")
    String accessKeyId;

    @Value("${aliyun.accesskey-secret}")
    String accessKeySecret;

    @Override
    public void registerEmail(String email) {
        String captcha = CaptchaUtils.getCaptcha(stringRedisTemplate, email);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom("2791656521@qq.com");
            messageHelper.setTo(email);
            messageHelper.setSubject("Captcha");
            messageHelper.setText(String.format("验证码为: %s, 5分钟内有效", captcha));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.toString());
        }
    }

    @Override
    public boolean verifyEmail(String email, String captcha) {
        return CaptchaUtils.verifyCaptcha(stringRedisTemplate, email, captcha);
    }

    @Override
    public void registerPhone(String phone) throws Exception{
        // HttpClient Configuration
        /*HttpClient httpClient = new ApacheAsyncHttpClientBuilder()
                .connectionTimeout(Duration.ofSeconds(10)) // Set the connection timeout time, the default is 10 seconds
                .responseTimeout(Duration.ofSeconds(10)) // Set the response timeout time, the default is 20 seconds
                .maxConnections(128) // Set the connection pool size
                .maxIdleTimeOut(Duration.ofSeconds(50)) // Set the connection pool timeout, the default is 30 seconds
                // Configure the proxy
                .proxy(new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("<your-proxy-hostname>", 9001))
                        .setCredentials("<your-proxy-username>", "<your-proxy-password>"))
                // If it is an https connection, you need to configure the certificate, or ignore the certificate(.ignoreSSL(true))
                .x509TrustManagers(new X509TrustManager[]{})
                .keyManagers(new KeyManager[]{})
                .ignoreSSL(false)
                .build();*/

        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                // Please ensure that the environment variables ALIBABA_CLOUD_ACCESS_KEY_ID and ALIBABA_CLOUD_ACCESS_KEY_SECRET are set.
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                //.securityToken(System.getenv("ALIBABA_CLOUD_SECURITY_TOKEN")) // use STS token
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();

        String captcha = CaptchaUtils.getCaptcha(stringRedisTemplate, phone);
        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(phone)
                .signName("疲劳驾驶检测看板平台")
                .templateCode("SMS_463156201")
                .templateParam(String.format("{\"code\":\"%s\"}", captcha))
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        // Asynchronously get the return value of the API request
        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        // Synchronously get the return value of the API request
        SendSmsResponse resp = response.get();
        if (!"OK".equals(resp.getBody().getCode())){
            log.warn(new Gson().toJson(resp));
            throw new BizException(BizExceptionType.NO_EFFECT, resp.getBody().getMessage());
        }
        // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/

        // Finally, close the client
        client.close();
    }

    @Override
    public boolean verifyPhone(String phone, String captcha) {
        return CaptchaUtils.verifyCaptcha(stringRedisTemplate, phone, captcha);
    }
}
