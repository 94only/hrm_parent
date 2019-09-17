package cn.wangningbo.hrm.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    // 注入routingKey。以后要根据routingKey发送消息到指定的消费者
    @Value("${rabbitmq.queues.routingKey}")
    private String routingKey;
    // 拿到交换机常量
    public static final String EXCHANGE_DIRECT_INFORM = RabbitmqConstants.EXCHANGE_DIRECT_INFORM;
    // 拿到队列常量
    public static final String QUEUE_INFORM_PAGESTATIC = RabbitmqConstants.QUEUE_INFORM_PAGESTATIC;

    /**
     * 交换机配置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     *
     * @return the exchange
     */
    public Exchange exchange_direct_inform() {
        //durable(true):进行持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT_INFORM).durable(true).build();
    }

    // 声明队列
    public Queue pageStaticQueue() {
        return new Queue(QUEUE_INFORM_PAGESTATIC);
    }

    // 获取特定名称的Bean
    public Binding BINDING_QUEUE_INFORM_HRMJOBSITE(@Qualifier(QUEUE_INFORM_PAGESTATIC) Queue queue,
                                                   @Qualifier(EXCHANGE_DIRECT_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

    // 提供routingKey的get和set方法
    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}
