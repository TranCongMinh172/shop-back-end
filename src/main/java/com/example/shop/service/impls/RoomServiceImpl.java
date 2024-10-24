package com.example.shop.service.impls;


import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.RoomChat;
import com.example.shop.repositories.BaseRepository;
import com.example.shop.repositories.RoomChatRepository;
import com.example.shop.repositories.UserRepository;
import com.example.shop.service.interfaces.RoomService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl extends BaseServiceImpl<RoomChat, Long> implements RoomService {
    private final RoomChatRepository roomChatRepository;
    private final UserRepository userRepository;


    public RoomServiceImpl(BaseRepository<RoomChat, Long> repository,
                           RoomChatRepository roomChatRepository, UserRepository userRepository) {
        super(repository, RoomChat.class);
        this.roomChatRepository = roomChatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RoomChat> getRoomsBySender(String sender) {
        return List.of();
    }

    @Override
    public String getRoomIdBySenderAndReceiver(String sender, String receiver, boolean createIfNotExists) throws DataNotFoundException {
        String roomId = concatRoomId(sender, receiver);
        Optional<RoomChat> optional = roomChatRepository.findBySenderAndReceiver(sender, receiver);
        if(!userRepository.existsByEmail(sender)) throw new DataNotFoundException("sender not found");
        if(!userRepository.existsByEmail(receiver)) throw new DataNotFoundException("receiver not found");
        if(optional.isEmpty()) {
            if(!createIfNotExists) {
                throw new DataNotFoundException("Room not found");
            }
            saveRoom(roomId, sender, receiver);
            saveRoom(roomId, receiver, sender);
        } else {
            roomId = optional.get().getRoomId();
        }
        return roomId;
    }

    private String concatRoomId(String sender, String receiver) {
        return sender + "_" + receiver;
    }

    private void saveRoom(String roomId, String sender, String receiver) {
        RoomChat roomChat = new RoomChat();
        roomChat.setRoomId(roomId);
        roomChat.setSender(sender);
        roomChat.setReceiver(receiver);
        roomChatRepository.save(roomChat);
    }


}
