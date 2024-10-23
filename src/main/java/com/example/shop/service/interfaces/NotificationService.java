package com.example.shop.service.interfaces;


import com.example.shop.dtos.responses.PageResponse;
import com.example.shop.models.Notification;

import java.util.List;

public interface NotificationService extends BaseService<Notification, Long> {
    void sendNotification(Notification notification);
    PageResponse<List<Notification>> getNotificationsByUserId(Long userId, int pageNo, int pageSize);
}
