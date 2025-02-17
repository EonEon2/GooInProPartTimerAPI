package org.gooinpro.gooinproparttimerapi.chatmessage.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageDTO {

    private String roomId;  //채팅방 ID

    private String senderEmail; //보내는 사람 email

    private String message; //message
}
