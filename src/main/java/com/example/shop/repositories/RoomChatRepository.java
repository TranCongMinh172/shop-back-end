package com.example.shop.repositories;

import com.example.shop.models.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomChatRepository extends BaseRepository<RoomChat, Long> {
    Optional<RoomChat> findBySenderAndReceiver(String sender, String receiver);
}