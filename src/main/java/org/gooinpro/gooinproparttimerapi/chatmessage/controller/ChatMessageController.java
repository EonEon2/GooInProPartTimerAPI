package org.gooinpro.gooinproparttimerapi.chatmessage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.chatmessage.domain.ChatMessageEntity;
import org.gooinpro.gooinproparttimerapi.chatmessage.dto.ChatMessageDTO;
import org.gooinpro.gooinproparttimerapi.chatmessage.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Log4j2
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {

        log.info("Sending message: {}", chatMessageDTO);

        ChatMessageEntity chatMessageEntity = chatMessageService.saveMessageService(chatMessageDTO);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + Long.parseLong(chatMessageDTO.getRoomId()), chatMessageEntity);
    }
}
