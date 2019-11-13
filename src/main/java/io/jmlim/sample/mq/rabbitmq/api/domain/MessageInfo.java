package io.jmlim.sample.mq.rabbitmq.api.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "message_logs")
@ToString
public class MessageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "service name is null")
    private String serviceName;
    @NotEmpty(message = "call api url is null")
    private String callApiUrl;

    private String errorUrl;

    @Setter
    private String type;
    @Setter
    private String exception;
}