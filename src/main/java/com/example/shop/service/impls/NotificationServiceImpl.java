package com.example.shop.service.impls;


import com.example.shop.dtos.responses.PageResponse;
import com.example.shop.models.Notification;
import com.example.shop.models.UserNotification;
import com.example.shop.models.enums.Scope;
import com.example.shop.repositories.BaseRepository;
import com.example.shop.repositories.NotificationRepository;
import com.example.shop.repositories.UserNotificationRepository;
import com.example.shop.service.interfaces.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl extends BaseServiceImpl<Notification, Long> implements NotificationService {
    private final SimpMessagingTemplate template;
    private final UserNotificationRepository userNotificationRepository;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(BaseRepository<Notification, Long> repository,
                                   SimpMessagingTemplate template, UserNotificationRepository userNotificationRepository,
                                   NotificationRepository notificationRepository) {
        super(repository, Notification.class);
        this.template = template;
        this.userNotificationRepository = userNotificationRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendNotification(Notification notification) {
        if (notification.getScope().equals(Scope.ALL)) {
            template.convertAndSend("/topic/notifications", notification);
        } else {
            List<UserNotification> userNotifications = userNotificationRepository.findAllByNotificationId(notification.getId());
            for (UserNotification userNotification : userNotifications) {
                String topic = String.format("user/%s/queue/notifications", userNotification.getUser().getEmail());
                template.convertAndSend(topic, notification);
            }
        }
    }

    @Override
    public PageResponse<List<Notification>> getNotificationsByUserId(Long userId, int pageNo, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "notificationDate");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Notification> pageNotifications = notificationRepository.getPageNotificationsByUserId(userId, pageable);
        List<Notification> notifications = pageNotifications.getContent();
        PageResponse<List<Notification>> result = new PageResponse<>();
        result.setData(notifications);
        result.setPageNo(pageNo);
        result.setTotalPage(pageNotifications.getTotalPages());
        result.setTotalElement(pageNotifications.getNumberOfElements());
        return result;
    }
}
