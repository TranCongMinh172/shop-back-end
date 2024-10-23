package com.example.shop.service.interfaces;


import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.RoomChat;

import java.util.List;

public interface RoomService extends BaseService<RoomChat, Long> {
    List<RoomChat> getRoomsBySender(String sender);
    String getRoomIdBySenderAndReceiver(String sender, String receiver, boolean createIfNotExists) throws DataNotFoundException;
}
