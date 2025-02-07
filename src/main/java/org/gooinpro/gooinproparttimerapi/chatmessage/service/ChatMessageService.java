package org.gooinpro.gooinproparttimerapi.chatmessage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.chatmessage.domain.ChatMessageEntity;
import org.gooinpro.gooinproparttimerapi.chatmessage.dto.ChatMessageDTO;
import org.gooinpro.gooinproparttimerapi.chatmessage.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

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
}
