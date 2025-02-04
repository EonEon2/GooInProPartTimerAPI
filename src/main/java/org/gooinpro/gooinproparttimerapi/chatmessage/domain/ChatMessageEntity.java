package org.gooinpro.gooinproparttimerapi.chatmessage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messagesChat")
public class ChatMessageEntity {

    @Id
    private String id;

    private String roomId;  //채팅방 id

    private String userId;  //채팅 보낸 user id;

    private String message; //채팅 message

    private Date sent_at;   //채팅 보낸 시간
}
