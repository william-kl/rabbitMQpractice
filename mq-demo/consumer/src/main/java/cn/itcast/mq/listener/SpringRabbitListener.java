package cn.itcast.mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;

@Component
public class SpringRabbitListener {
    /**
     * Basic Queue
     */
//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueueMessage(String msg) throws InterruptedException {
//        System.out.println("spring 消费者接受到消息 ： [" + msg + "]");
//    }
//

    /**
     * Work Queue
     */
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("spring 消费者1接受到消息 ： [" + msg + "]" + LocalTime.now());
        Thread.sleep(20);//每秒钟50条
    }

    /*publisher共发了50条，q1处理奇数的message25条, q2处理偶数的message25条，平均分配了 */
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2...........接受到消息 ： [" + msg + "]" + LocalTime.now());
        Thread.sleep(200);//每秒钟5个
    }


    /**
     * 发布，订阅Fanout
     */
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanQueue1(String msg) throws InterruptedException {
        System.out.println("消费者接受到fanout.queue1的消息 ： [" + msg + "]");
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanQueue2(String msg) throws InterruptedException {
        System.out.println("消费者接受到fanout.queue2的消息 ： [" + msg + "]");
    }


    /**
     * 发布，订阅DirectExchange
     * use @RabbitListener annotation, binding 2 queues and same exchange directly
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "itcast.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String msg) {
        System.out.println("consumer received message from directQ1：【"+ msg +"】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "itcast.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String msg) {
        System.out.println("consumer received message from directQ2：【"+ msg +"】");
    }

    /**
     * 发布，订阅TopicExchange
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "itcast.topic", type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void listenTopicQueue1(String msg) throws InterruptedException {
        System.out.println("consumer received message from topicQ1 ： [" + msg + "]");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "itcast.topic", type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void listenTopicQueue2(String msg) throws InterruptedException {
        System.out.println("consumer received message from topicQ2 ： [" + msg + "]");
    }


    /**
     * 消息转换器
     */
    @RabbitListener(queues = "object.queue")
    public void listenerObjectQueue(Map<String, Object> msg){
        System.out.println("received message from object.queue: " + msg);
    }
}