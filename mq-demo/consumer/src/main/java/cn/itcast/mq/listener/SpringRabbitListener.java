package cn.itcast.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class SpringRabbitListener {
//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueueMessage(String msg) throws InterruptedException {
//        System.out.println("spring 消费者接受到消息 ： [" + msg + "]");
//    }

    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("spring 消费者1接受到消息 ： [" + msg + "]" + LocalTime.now());
        Thread.sleep(20);//每秒钟50条
    }

    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2...........接受到消息 ： [" + msg + "]" + LocalTime.now());
        Thread.sleep(200);//每秒钟5个
    }
}
