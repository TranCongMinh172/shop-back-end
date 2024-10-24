package com.example.shop.controllers;
import com.example.shop.dtos.requests.CommentDto;
import com.example.shop.dtos.responses.CommentResponse;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.MediaTypeNotSupportException;
import com.example.shop.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final SimpMessagingTemplate messagingTemplate;
    private final CommentService commentService;

    @PostMapping("/send")
    public com.example.shop.dtos.requests.responses.ResponseSuccess<?> send(@ModelAttribute CommentDto commentDto) throws DataNotFoundException, IOException, MediaTypeNotSupportException {
        CommentResponse comment = commentService.addComment(commentDto);
        messagingTemplate.convertAndSend("/topic/product/" + commentDto.getProductId(),
                comment);
        return new com.example.shop.dtos.requests.responses.ResponseSuccess<>(
                HttpStatus.OK.value(),
                "oke"
        );
    }

    @GetMapping("/page-comment")
    public com.example.shop.dtos.requests.responses.ResponseSuccess<?> pageComments(@RequestParam(defaultValue = "1") int pageNo,
                                                                                    @RequestParam(defaultValue = "10") int pageSize,
                                                                                    @RequestParam(required = false) String[] search,
                                                                                    @RequestParam(required = false) String[] sort) throws DataNotFoundException, IOException, MediaTypeNotSupportException {

        return new com.example.shop.dtos.requests.responses.ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get comment successfully",
                commentService.getPageData(pageNo, pageSize, search, sort)
        );
    }
}
