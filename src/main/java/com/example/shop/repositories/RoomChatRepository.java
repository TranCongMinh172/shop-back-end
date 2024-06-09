package com.example.shop.repositories;

import com.example.shop.models.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomChatRepository extends JpaRepository<RoomChat, Long> {
}