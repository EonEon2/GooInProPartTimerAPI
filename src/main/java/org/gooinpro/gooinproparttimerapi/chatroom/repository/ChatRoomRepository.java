package org.gooinpro.gooinproparttimerapi.chatroom.repository;

import org.gooinpro.gooinproparttimerapi.chatroom.domain.ChatRoomEntity;
import org.gooinpro.gooinproparttimerapi.chatroom.repository.search.ChatRoomSearch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoomEntity, String>, ChatRoomSearch {
}
