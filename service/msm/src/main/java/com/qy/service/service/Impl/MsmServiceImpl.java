package com.qy.service.service.Impl;

import com.qy.service.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author qinyue
 * @create 2022-10-09 22:10:00
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${spring.mail.username}")
    private String HostEmail;

    @Override
    public void verifyCode(String userEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 发件人
        simpleMailMessage.setFrom(HostEmail);
        // 收件人
        simpleMailMessage.setTo(userEmail);
        // 邮件主题
        simpleMailMessage.setSubject("墨课教育验证码");
        // 验证码
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i=0;i<6;i++) {
            code.append(random.nextInt(10));
        }
        // Redis
        redisTemplate.opsForValue().set(userEmail, code.toString(), 10, TimeUnit.MINUTES);

        // 邮件内容
        String content = "感谢您使用墨课教育, 您的验证码是" + code;
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }
}
