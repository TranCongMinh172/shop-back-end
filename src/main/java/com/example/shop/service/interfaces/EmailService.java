package com.example.shop.service.interfaces;

import com.example.shop.utils.EmailDetails;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {
    String sendSimpleMail(EmailDetails emailDetails);
    String sendMailWithAttachment(EmailDetails emailDetails) throws MessagingException, IOException;
    void sendHtmlMail(EmailDetails emailDetails) throws MessagingException;
}
