package com.example.shop.repositories;

import com.example.shop.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends BaseRepository<Message, String> {
}