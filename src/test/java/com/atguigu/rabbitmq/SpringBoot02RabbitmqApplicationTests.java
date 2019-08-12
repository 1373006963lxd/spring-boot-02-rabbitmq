package com.atguigu.rabbitmq;

import com.atguigu.rabbitmq.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.plugin2.message.Message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot02RabbitmqApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;
/*

1.单播（点对点）

发送数据


*/

    @Test
    public void contextLoads() {
//        Message需要自己构造一个，定义消息体内容和消息头
//        rabbitTemplate.send(exchange,rountkey,message);


//        object默认当成消息体，只需要传入要发送de对象，自动序列化发送给rabbitmq
//        rabbitTemplate.convertAndSend(exchange,rountkey,message);

        Map<String,Object> map = new HashMap<>();
        map.put("msg","这是第一个消息");
        map.put("data", Arrays.asList("helloworld",123,true));
//        对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange.direct","atguigu",map);
    }


/*
* 收到消息以后就没有了-----direct（点对点）
*
* */

    @Test
    public void receive(){
        Object o = rabbitTemplate.receiveAndConvert("atguigu.emps");
        System.out.println(o.getClass());
        System.out.println(o);
    }
/*
*
* 发送一个实体book测试json格式
*
* */

    @Test
    public void sendObject(){
        rabbitTemplate.convertAndSend("exchange.direct","atguigu",new Book("金瓶梅","lxd"));
    }

    /*
    * 广播演示------不用定义routingKey，因为是广播给交换机的全部队列
    *
    *
    * */

    @Test
    public void GBOsend(){
        rabbitTemplate.convertAndSend("exchange.fanout","",new Book("三国演义","罗贯中"));
    }


    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void createExchange(){
        /*amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        System.out.println("创建完成");*/

//        amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));


        //创建绑定规则  将exchange与queue绑定
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqp.hah",null));


    }

}
