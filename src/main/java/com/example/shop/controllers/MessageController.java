package com.example.shop.controllers;

import com.example.shop.dtos.requests.MessageDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.MediaTypeNotSupportException;
import com.example.shop.service.interfaces.MessageService;
import com.example.shop.utils.ValidToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final ValidToken validToken;

    @PostMapping("/send")
    public com.example.shop.dtos.requests.responses.ResponseSuccess<?> senMessage(@ModelAttribute @Valid MessageDto messageDto,
                                                                                  HttpServletRequest req) throws DataNotFoundException, IOException, MediaTypeNotSupportException {
        validToken.valid(messageDto.getSender(), req);
        return new com.example.shop.dtos.requests.responses.ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                messageService.sendMessage(messageDto)
        );
    }

    @GetMapping("/{roomId}")
    public com.example.shop.dtos.requests.responses.ResponseSuccess<?> getMessage(@PathVariable String roomId,
                                                                                  @RequestParam String email,
                                                                                  @RequestParam(defaultValue = "1") int pageNo,
                                                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                                                  HttpServletRequest req) throws DataNotFoundException, IOException, MediaTypeNotSupportException {
        validToken.valid(email, req);
        String roomSearch = "roomId-" + roomId;
        String[] search = {roomSearch};
        String[] sort = {"sendDate:desc"};
        return new com.example.shop.dtos.requests.responses.ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                messageService.getPageData(pageNo, pageSize, search, sort)
        );
    }

}
