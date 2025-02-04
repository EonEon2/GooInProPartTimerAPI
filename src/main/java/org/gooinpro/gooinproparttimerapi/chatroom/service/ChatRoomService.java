package org.gooinpro.gooinproparttimerapi.chatroom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.chatroom.domain.ChatRoomEntity;
import org.gooinpro.gooinproparttimerapi.chatroom.dto.ChatRoomAddDTO;
import org.gooinpro.gooinproparttimerapi.chatroom.dto.ChatRoomFindDTO;
import org.gooinpro.gooinproparttimerapi.chatroom.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    //채팅방 새로 만들기
    public String addChatRoom(ChatRoomAddDTO chatRoomAddDTO) {

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .roomName(chatRoomAddDTO.getRoomName())
                .roomCreated_at(Date.from(Instant.now()))
                .roomUpdated_at(Date.from(Instant.now()))
                .createdBy(chatRoomAddDTO.getCreatedBy())
                .participants(chatRoomAddDTO.getParticipants())
                .build();

        chatRoomRepository.save(chatRoomEntity);

        return "Successfully added chat room " + chatRoomAddDTO.getRoomName();
    }

    //채팅방 찾기(1대 1 채팅)
    public ChatRoomEntity findChatRoom(ChatRoomFindDTO chatRoomFindDTO) {

        ChatRoomEntity chatRoom = null;

        return null;
    }
}
