package io.jmlim.sample.mq.rabbitmq.configuration.retry;

import io.jmlim.sample.mq.rabbitmq.api.domain.MessageInfo;
import io.jmlim.sample.mq.rabbitmq.api.repository.MessageInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorRecoverer extends RejectAndDontRequeueRecoverer {

    private final MessageInfoRepository messageInfoRepository;

    /**
     * 에러시 처리...
     *
     * @param message
     * @param cause
     */
    @Override
    public void recover(Message message, Throwable cause) {
        if (this.log.isWarnEnabled()) {
            this.log.warn("Retries exhausted for message " + message, cause);
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(message.getBody());
        ObjectInput in = null;
        MessageInfo messageInfo = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            messageInfo = (MessageInfo) o;

            messageInfoRepository.insert(messageInfo);
        } catch (IOException e) {
            // 이부분은 어떻게 할 것인가.. 그냥 로그로 남기기?
            log.error("ErrorRecoverer Byte Array Input Stream", e);
        } catch (ClassNotFoundException e) {
            // 이부분은 어떻게 할 것인가22..
            log.error("ErrorRecoverer ClassNotFoundException ", e);
        }

        throw new ListenerExecutionFailedException("Retry Policy Exhausted",
                new AmqpRejectAndDontRequeueException(cause), message);
    }
}