package com.example.shop.service.interfaces;



import com.example.shop.dtos.requests.MessageDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.MediaTypeNotSupportException;
import com.example.shop.models.Message;

import java.io.IOException;

public interface MessageService extends BaseService<Message, String> {
    Message sendMessage(MessageDto messageDto) throws IOException, DataNotFoundException, MediaTypeNotSupportException;
}
