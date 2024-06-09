package com.example.shop.models;

import com.example.shop.models.idClass.UserNotificationId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_notifications")
@IdClass(UserNotificationId.class)
public class UserNotification {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;
    private boolean isSeen;
}
