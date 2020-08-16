package com.dlq.amqp;

import com.dlq.amqp.bean.Book;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Springboot02AmqpApplicationTests {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	AmqpAdmin amqpAdmin;

	@Test
	public void createExchange() {
		amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
		System.out.println("创建完成");
	}

	@Test
	public void createQueue() {
		amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));
	}

	@Test
	public void testBinding() {
		amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqp.haha",null));
	}

	/**
	 * 1、单播（点对点）
	 */
	@Test
	void contextLoads() {
		//message需要自己构造一个；定义消息体内容和消息头
//		rabbitTemplate.send(exchange, routingKey, message);
		//object默认当成消息体只需要传入要发送的对象，自动序列化发送给rabbitmq
//		rabbitTemplate.convertAndSend(exchange, routingKey,object);
		Map<String,Object> map = new HashMap<>();
		map.put("msg","这是第一个消息");
		map.put("data", Arrays.asList("helloworld",123,true));
		//对象被默认序列化以后发送出去
		rabbitTemplate.convertAndSend("exchange.direct","dlq.news",new Book("西游记","吴承恩"));
	}

	//接收数据，如何将数据自动的转为json发送出去
	@Test
	public void testReceive() {
		Object convert = rabbitTemplate.receiveAndConvert("dlq.news");
		System.out.println(convert.getClass());
		System.out.println(convert);
	}

	/**
	 * 广播
	 */
	@Test
	public void testSendMsg() {
		rabbitTemplate.convertAndSend("exchange.fanout","",new Book("红楼梦","罗贯中"));
	}
}
