package io.jmlim.sample.mq.rabbitmq.api.exception;

import lombok.Getter;

public class MessageInfoException extends RuntimeException {
    @Getter
    private String message;

    public MessageInfoException(String message) {
        super(message);

        this.message = message;
    }
}