package com.example.shop.models.idClass;

import com.example.shop.models.Notification;
import com.example.shop.models.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
public class UserNotificationId  implements Serializable {
    private User user;
    private Notification notification;
}
