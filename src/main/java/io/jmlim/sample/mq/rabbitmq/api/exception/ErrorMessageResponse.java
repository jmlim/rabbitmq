package io.jmlim.sample.mq.rabbitmq.api.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessageResponse {
    private String errormsg;
    private String status;
}
