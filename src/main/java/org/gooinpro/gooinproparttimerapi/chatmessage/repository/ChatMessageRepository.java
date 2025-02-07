package org.gooinpro.gooinproparttimerapi.chatmessage.repository;

import org.gooinpro.gooinproparttimerapi.chatmessage.domain.ChatMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, String> {
}
