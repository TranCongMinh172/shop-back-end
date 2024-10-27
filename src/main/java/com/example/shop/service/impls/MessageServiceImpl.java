package com.example.shop.service.impls;

import com.example.shop.dtos.requests.MessageDto;
import com.example.shop.dtos.responses.MessageResponse;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.MediaTypeNotSupportException;
import com.example.shop.models.Message;
import com.example.shop.models.RoomChat;
import com.example.shop.models.enums.MessageType;
import com.example.shop.repositories.BaseRepository;
import com.example.shop.repositories.RoomChatRepository;
import com.example.shop.service.interfaces.MessageService;
import com.example.shop.service.interfaces.RoomService;
import com.example.shop.utils.S3Upload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {

    private final Set<String> IMAGE_EXTENSIONS = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif", "bmp"));
    private final Set<String> VIDEO_EXTENSIONS = new HashSet<>(Arrays.asList("mp4", "avi", "mov", "mkv", "webm"));
    private final S3Upload s3Upload;
    private final RoomService roomService;
    private final RoomChatRepository roomChatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageServiceImpl(BaseRepository<Message, String> repository,
                              S3Upload s3Upload,
                              RoomService roomService,
                              RoomChatRepository roomChatRepository,
                              SimpMessagingTemplate messagingTemplate) {
        super(repository, Message.class);
        this.s3Upload = s3Upload;
        this.roomService = roomService;
        this.roomChatRepository = roomChatRepository;
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public Message sendMessage(MessageDto messageDto) throws IOException, DataNotFoundException, MediaTypeNotSupportException {
        Message message = new Message();
        message.setSender(messageDto.getSender());
        message.setReceiver(messageDto.getReceiver());
        message.setMessageType(MessageType.TEXT);
        message.setSendDate(LocalDateTime.now());
        message.setContent(messageDto.getMessage());
        if(messageDto.getFile() != null) {
            message.setMessageType(getMessageType(messageDto.getFile()));
            message.setPath(s3Upload.uploadFile(messageDto.getFile()));
        }
        message.setId(UUID.randomUUID().toString());
        message.setRoomId(roomService
                .getRoomIdBySenderAndReceiver(messageDto.getSender(),
                        messageDto.getReceiver(),
                        true));
        super.save(message);
        RoomChat roomReceiver = roomChatRepository.findBySenderAndReceiver(message.getReceiver(), message.getSender())
                .orElseThrow(() -> new DataNotFoundException("room not found"));
        roomReceiver.setSeen(false);
        roomChatRepository.save(roomReceiver);
        sendToSocket(message);
        return message;
    }


    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex >= 0 && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex + 1);
        }
        return "";
    }

    private MessageType getMessageType(MultipartFile file) throws MediaTypeNotSupportException {
        String filename = file.getOriginalFilename();
        Set<String> IMAGE_EXTENSIONS = Set.of("jpg", "png", "gif", "jpeg", "jfif");
        Set<String> VIDEO_EXTENSIONS = Set.of("mp4", "avi", "mov", "mkv");
        if (filename == null) {
            return MessageType.TEXT;
        }
        String extension = getFileExtension(filename);
        if(IMAGE_EXTENSIONS.contains(extension.toLowerCase())) {
            return MessageType.IMAGE;
        }
        if(VIDEO_EXTENSIONS.contains(extension.toLowerCase())) {
            return MessageType.VIDEO;
        }
        throw new MediaTypeNotSupportException("only allow sending image or video");
    }

    private void sendToSocket(Message message) {
        MessageResponse<Message> response = new MessageResponse<>();
        response.setData(message);
        response.setType("message");
        messagingTemplate.convertAndSendToUser(message.getReceiver(),"/queue/notifications", response);
    }
}
