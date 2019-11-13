package io.jmlim.sample.mq.rabbitmq.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class MqExceptionHandler {

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler({MessageInfoException.class})
    @ResponseBody
    public ErrorMessageResponse handleMessageException(WebRequest request, MessageInfoException e) {

        log.error("--> MessageExceptionHandler : MessageException", e);

        ErrorMessageResponse failure = ErrorMessageResponse
                .builder()
                .errormsg(e.getMessage())
                .status("failure").build();

        return failure;
    }
}
