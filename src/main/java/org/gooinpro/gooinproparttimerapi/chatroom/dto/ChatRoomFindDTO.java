package org.gooinpro.gooinproparttimerapi.chatroom.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
//1대 1 채팅 찾기 DTO
public class ChatRoomFindDTO {

    private String senderEmail;    //보내는 사람 Email

    private String recipientEmail;  //받는 사람 Email
}
