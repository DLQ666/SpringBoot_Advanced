package com.dlq.amqp.service;

import com.dlq.amqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-16 20:23
 */
@Service
public class BookService {

    @RabbitListener(queues = "dlq.news")
    public void receive(Book book){
        System.out.println("收到消息："+book);
    }

    @RabbitListener(queues = "dlq")
    public void receive2(Message message){
        System.out.println(message.getBody());
        System.out.println("---");
        System.out.println(message.getMessageProperties());
    }
}
