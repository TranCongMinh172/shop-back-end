package com.example.shop.repositories;

import com.example.shop.models.UserNotification;
import com.example.shop.models.idClass.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationRepository extends BaseRepository<UserNotification, UserNotificationId> {
    List<UserNotification> findAllByNotificationId(Long id);
}