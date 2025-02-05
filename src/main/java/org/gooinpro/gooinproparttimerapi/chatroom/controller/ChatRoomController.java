package org.gooinpro.gooinproparttimerapi.chatroom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.chatroom.domain.ChatRoomEntity;
import org.gooinpro.gooinproparttimerapi.chatroom.dto.ChatRoomAddDTO;
import org.gooinpro.gooinproparttimerapi.chatroom.service.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chatroom")
@RequiredArgsConstructor
@Log4j2
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("create")
    public ResponseEntity<ChatRoomEntity> test1(@RequestBody ChatRoomAddDTO dto) {

        log.info("testestestestestesetestestestestest");
        log.info(dto.toString());

        return ResponseEntity.ok(chatRoomService.addChatRoom(dto));
    }
}
