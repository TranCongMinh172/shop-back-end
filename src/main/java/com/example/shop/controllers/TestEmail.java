package com.example.shop.controllers;

import com.example.shop.service.interfaces.EmailService;
import com.example.shop.utils.EmailDetails;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/email")
@RequiredArgsConstructor
public class TestEmail {
    private final EmailService emailService;

    @PostMapping
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        return  emailService.sendSimpleMail(emailDetails);
    }
    @PostMapping("/attach-file")
    public String sendAttachment(@ModelAttribute EmailDetails emailDetails) throws MessagingException, IOException {
        return  emailService.sendMailWithAttachment(emailDetails);
    }
    @PostMapping("/mail-html")
    public String sendHtmlEmail(@ModelAttribute EmailDetails emailDetails) throws MessagingException {
       emailService.sendHtmlMail(emailDetails);
       return "send email successfully";
    }
}
