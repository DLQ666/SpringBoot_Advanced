package com.dlq.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.security.cert.TrustAnchor;

@SpringBootTest
class Springboot04TaskApplicationTests {

	@Autowired
	JavaMailSenderImpl javaMailSender;

	@Test
	void contextLoads() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		//邮件设置
		simpleMailMessage.setSubject("测试-Java发送");
		simpleMailMessage.setText("啊哈哈哈哈哈哈哈哈哈哈");

		simpleMailMessage.setTo("dlq096@gmail.com");
		simpleMailMessage.setFrom("1114626450@qq.com");

		javaMailSender.send(simpleMailMessage);
	}

	@Test
	public void test02() throws MessagingException {
		//1、创建一个复杂的消息邮件
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		//邮件设置
		helper.setSubject("测试-Java发送");
		helper.setText("<b style='color:red'>啊哈哈哈哈哈哈哈哈哈哈</b>",true);

		helper.setTo("dlq096@gmail.com");
		helper.setFrom("1114626450@qq.com");

		//上传文件
		helper.addAttachment("1.jpg",new File("C:\\Users\\11146\\Pictures\\AMD.jpg"));
		helper.addAttachment("2.jpg",new File("C:\\Users\\11146\\Pictures\\7d23ad95ca25f0b4.jpg"));

		javaMailSender.send(mimeMessage);
	}
}
