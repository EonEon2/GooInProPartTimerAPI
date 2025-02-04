package org.gooinpro.gooinproparttimerapi.chatroom.dto;

import lombok.Data;

@Data
//1대 1 채팅 찾기 DTO
public class ChatRoomFindDTO {

    private Long pno;   //Primary Key

    private String email;   //채팅 상대 이메일

    private String role;    //Admin or Employer or partTimer
}
