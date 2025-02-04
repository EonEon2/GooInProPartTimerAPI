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

    private String sender; // 보내는사람

    private String receiver; // 받는사람

    private String message;

    private Date timestamp;

    private String roomId;
}
