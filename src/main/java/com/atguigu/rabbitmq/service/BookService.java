package com.atguigu.rabbitmq.service;

import com.atguigu.rabbitmq.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {


    /*监听atguigu的队列*/
    @RabbitListener(queues = "atguigu")
    public void receive(Book book){
        System.out.println("收到消息"+book);
    }


    @RabbitListener(queues = "atguigu.emps")
    public void receive02(Message message){
        System.out.println(message.getBody());
        System.out.println("==========================");
        System.out.println(message.getMessageProperties());
    }
}
