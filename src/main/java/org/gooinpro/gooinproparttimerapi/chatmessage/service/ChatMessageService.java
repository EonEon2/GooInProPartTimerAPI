package org.gooinpro.gooinproparttimerapi.chatmessage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.chatmessage.domain.ChatMessageEntity;
import org.gooinpro.gooinproparttimerapi.chatmessage.dto.ChatMessageDTO;
import org.gooinpro.gooinproparttimerapi.chatmessage.dto.ChatMessageReadDTO;
import org.gooinpro.gooinproparttimerapi.chatmessage.repository.ChatMessageRepository;
import org.gooinpro.gooinproparttimerapi.chatroom.dto.ChatRoomListDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final MongoTemplate mongoTemplate;

    public ChatMessageEntity saveMessageService(ChatMessageDTO chatMessageDTO) {

        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .roomId(chatMessageDTO.getRoomId())
                .senderEmail(chatMessageDTO.getSenderEmail())
                .message(chatMessageDTO.getMessage())
                .sentAt(Date.from(Instant.now()))
                .build();

        ChatMessageEntity savedMessage = chatMessageRepository.save(chatMessageEntity);

        log.info("Successfully saved Chat Message");

        return savedMessage;
    }

    //채팅방 들어갔을 때 메세지 가져오기
    public PageResponseDTO<ChatMessageReadDTO> getMessageService(String roomId, PageRequestDTO pageRequestDTO) {

        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize();
        int skip = (page - 1) * size;

        Criteria criteria = Criteria.where("roomId").is(roomId);

        Query query = new Query(criteria)
                .with(Sort.by(Sort.Order.desc("sentAt")))  // sentAt 기준 내림차순 정렬
                .skip(skip)                               // (page - 1) * size
                .limit(size);                             // 페이지 크기 설정

        log.info("MongoDB Query: {}", query);

        List<ChatMessageReadDTO> dtoList = mongoTemplate.find(query, ChatMessageReadDTO.class, "chat_messages");
        int totalCount = (int) mongoTemplate.count(Query.query(criteria), "chat_messages");

        return new PageResponseDTO<>(dtoList, pageRequestDTO, totalCount);
    }
}
