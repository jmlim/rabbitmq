package io.jmlim.sample.mq.rabbitmq.api.consumer;

import io.jmlim.sample.mq.rabbitmq.api.domain.MessageInfo;
import io.jmlim.sample.mq.rabbitmq.api.repository.MessageInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveService {

    private final MessageInfoRepository messageInfoRepository;

    private final RestTemplate restTemplate;

    @RabbitListener(queues = "relay.event")
    public void receive(MessageInfo messageInfo) {

        String callApiUrl = messageInfo.getCallApiUrl();
        try {
            UriComponentsBuilder apiUrlBuilder = UriComponentsBuilder.fromUriString(callApiUrl);
            log.debug("request api url --> {}", apiUrlBuilder.toUriString());

            messageInfo.setType("success");

            restTemplate.getForObject(apiUrlBuilder.toUriString(), String.class);
        } catch (Exception e) {
            log.error("api error ", e);
            String errorUrl = messageInfo.getErrorUrl();
            String stackTrace = ExceptionUtils.getStackTrace(e);
            if (Objects.nonNull(errorUrl)) {
                UriComponentsBuilder errorUrlBuilder = UriComponentsBuilder.fromUriString(errorUrl);
                restTemplate.getForObject(errorUrlBuilder.toUriString(), String.class);
            }

            messageInfo.setType("error");
            messageInfo.setException(stackTrace);
        }

        log.debug("insert message info : {}", messageInfo);
        messageInfoRepository.insert(messageInfo);
    }
}