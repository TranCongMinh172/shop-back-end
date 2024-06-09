package com.example.shop.repositories;

import com.example.shop.models.UserNotification;
import com.example.shop.models.idClass.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationId> {
}