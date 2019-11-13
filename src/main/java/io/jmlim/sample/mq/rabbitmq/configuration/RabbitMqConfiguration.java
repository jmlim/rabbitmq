package io.jmlim.sample.mq.rabbitmq.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: Jeongmuk Lim
 */
@Configuration
public class RabbitMqConfiguration {
    private static final String key = "relay.event";

    @Bean
    public Queue queue() {
        return new Queue(key, false);
    }
}
