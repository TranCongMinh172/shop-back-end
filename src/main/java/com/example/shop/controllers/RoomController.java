package com.example.shop.controllers;


import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.MediaTypeNotSupportException;
import com.example.shop.service.interfaces.RoomService;
import com.example.shop.utils.ValidToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final ValidToken validToken;

    @GetMapping("/{email}")
    public com.example.shop.dtos.requests.responses.ResponseSuccess<?> getRoom(@PathVariable String email,
                                                                               @RequestParam(defaultValue = "1") int pageNo,
                                                                               @RequestParam(defaultValue = "10") int pageSize,
                                                                               HttpServletRequest req) throws DataNotFoundException, IOException, MediaTypeNotSupportException {
        validToken.valid(email, req);
        String emailSearch = "sender-" + email;
        String[] search = {emailSearch};
        String[] sort = {"isSeen:asc"};
        return new com.example.shop.dtos.requests.responses.ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                roomService.getPageData(pageNo, pageSize, search, sort)
        );
    }
}
