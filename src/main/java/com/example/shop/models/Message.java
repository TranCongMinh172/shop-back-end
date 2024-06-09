package com.example.shop.models;

import com.example.shop.models.enums.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @Column(name = "message_id")
    private String messageId;
    private String sender;
    private String receiver;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomChat roomChat;
    private Date sendDate;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
}
