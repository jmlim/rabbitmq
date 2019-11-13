package io.jmlim.sample.mq.rabbitmq.api.repository;

import io.jmlim.sample.mq.rabbitmq.api.domain.MessageInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageInfoRepository extends MongoRepository<MessageInfo, String> {
}