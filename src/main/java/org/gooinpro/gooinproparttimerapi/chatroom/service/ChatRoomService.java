package org.gooinpro.gooinproparttimerapi.chatroom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.chatroom.domain.ChatRoomEntity;
import org.gooinpro.gooinproparttimerapi.chatroom.dto.ChatRoomAddDTO;
import org.gooinpro.gooinproparttimerapi.chatroom.dto.ChatRoomFindDTO;
import org.gooinpro.gooinproparttimerapi.chatroom.repository.ChatRoomRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MongoTemplate mongoTemplate;

    //채팅방 새로 만들기
    public ChatRoomEntity addChatRoom(ChatRoomAddDTO chatRoomAddDTO) {

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .roomName(chatRoomAddDTO.getRoomName())
                .roomCreated_at(Date.from(Instant.now()))
                .roomUpdated_at(Date.from(Instant.now()))
                .createdBy(chatRoomAddDTO.getCreatedBy())
                .participants(chatRoomAddDTO.getParticipants())
                .build();

        return chatRoomRepository.save(chatRoomEntity);
    }

    //채팅방 찾기(1대 1 채팅)
    public ChatRoomEntity findChatRoom(ChatRoomFindDTO chatRoomFindDTO) {

        Query query = new Query();
        query.addCriteria(Criteria.where("participants").size(2)
                .and("participants.email").all(chatRoomFindDTO.getSenderEmail(), chatRoomFindDTO.getRecipientEmail()));

        ChatRoomEntity chatRoom = mongoTemplate.findOne(query, ChatRoomEntity.class);

        if (chatRoom == null) {

            ChatRoomAddDTO dto = new ChatRoomAddDTO();


            dto.setRoomName(null);
            dto.setCreatedBy(chatRoomFindDTO.getSenderEmail());

            chatRoom = addChatRoom(dto);
        }

        return null;
    }
}
