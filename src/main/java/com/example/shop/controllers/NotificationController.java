package com.example.shop.controllers;

import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.MediaTypeNotSupportException;
import com.example.shop.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public com.example.shop.dtos.requests.responses.ResponseSuccess<?> getNotifications(  @PathVariable Long userId,
                                                                                          @RequestParam(defaultValue = "1") int pageNo,
                                                                                          @RequestParam(defaultValue = "10") int pageSize) throws DataNotFoundException, IOException, MediaTypeNotSupportException {
        return new com.example.shop.dtos.requests.responses.ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get notifications successfully",
                notificationService.getNotificationsByUserId(userId, pageNo, pageSize)
        );
    }
}
