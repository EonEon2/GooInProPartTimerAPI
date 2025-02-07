package org.gooinpro.gooinproparttimerapi.chatroom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRoomListDTO {

    private String roomName;    //채팅방 이름

    private String message; //마지막 메세지 내용

    private Date sent_at;   //마지막 메세지 시간
}
