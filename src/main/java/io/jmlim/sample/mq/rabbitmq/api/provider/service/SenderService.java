package io.jmlim.sample.mq.rabbitmq.api.provider.service;

import io.jmlim.sample.mq.rabbitmq.api.domain.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenderService {

    private final RabbitMessagingTemplate rabbitMessagingTemplate;

    public void send(MessageInfo messageInfo) {
        rabbitMessagingTemplate.convertAndSend("relay.event", messageInfo);
    }
}
