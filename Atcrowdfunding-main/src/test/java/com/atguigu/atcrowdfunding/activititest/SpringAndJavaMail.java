package com.atguigu.atcrowdfunding.activititest;

import com.atguigu.atcrowdfunding.util.DesUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * spring和javaMail集成测试
 */
public class SpringAndJavaMail {
    //需求：使用java程序发送邮件
    public static void main(String[] args) throws Exception {
        //获取spring容器
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring/spring-*.xml");
        //邮件发送器，由spring框架提供的
        JavaMailSenderImpl sendMail = (JavaMailSenderImpl)applicationContext.getBean("sendMail");
        //设置编码
        sendMail.setDefaultEncoding("utf-8");
        MimeMessage mailMimeMessage = sendMail.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMimeMessage);
        helper.setSubject("标题");
        StringBuilder content = new StringBuilder();
        //设置内容
        //密码加密
        String param="userid123";
        param=DesUtil.encrypt(param,"abcdefghijklmnopquvwxyz");
        content.append("<a href='http://www.atcrowdfunding.com/test/act.do?p="+param+"'>激活链接</a>'>");
        helper.setText(content.toString(),true);
        helper.setFrom("admin@atguigu.com");
        helper.setTo("test@atguigu.com");
        sendMail.send(mailMimeMessage);


    }
}
