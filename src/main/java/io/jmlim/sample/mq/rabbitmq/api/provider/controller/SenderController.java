package io.jmlim.sample.mq.rabbitmq.api.provider.controller;

import io.jmlim.sample.mq.rabbitmq.api.domain.MessageInfo;
import io.jmlim.sample.mq.rabbitmq.api.exception.MessageInfoException;
import io.jmlim.sample.mq.rabbitmq.api.provider.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * send 코드는 분리 필요.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/send")
public class SenderController {

    private final SenderService senderService;

    @PostMapping
    public ResponseEntity<MessageInfo> sendMessage(@Valid @RequestBody MessageInfo messageInfo, Errors errors) {

        log.debug("=====> send message : ", messageInfo);

        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new MessageInfoException(errors.getFieldError().getDefaultMessage());
        }

        senderService.send(messageInfo);

        return new ResponseEntity<>(messageInfo, HttpStatus.OK);
    }
}