package org.gooinpro.gooinproparttimerapi.chatroom.repository.search;

import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.chatroom.domain.ChatRoomEntity;
import org.gooinpro.gooinproparttimerapi.chatroom.dto.ChatRoomListDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Log4j2
public class ChatRoomSearchImpl extends QuerydslRepositorySupport implements ChatRoomSearch{

    public ChatRoomSearchImpl() {
        super(ChatRoomEntity.class);
    }

    @Override
    public PageResponseDTO<ChatRoomListDTO> chatRoomList(Long pno) {
        return null;
    }
}
