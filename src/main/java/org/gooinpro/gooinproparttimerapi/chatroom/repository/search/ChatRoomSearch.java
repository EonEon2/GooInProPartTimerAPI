package org.gooinpro.gooinproparttimerapi.chatroom.repository.search;

import org.gooinpro.gooinproparttimerapi.chatroom.dto.ChatRoomListDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;

public interface ChatRoomSearch {

    PageResponseDTO<ChatRoomListDTO> chatRoomList(Long pno); //내 채팅방 리스트
}
