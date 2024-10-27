package com.example.shop.models.idClass;

import com.example.shop.models.Notification;
import com.example.shop.models.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class UserNotificationId  implements Serializable {
    private User user;
    private Notification notification;
}
