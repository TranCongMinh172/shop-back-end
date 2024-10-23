package com.example.shop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms_chat")
public class RoomChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "room_id", nullable = false)
    private String roomId;
    @Column(nullable = false)
    private String sender;
    @Column(nullable = false)
    private String receiver;
    @Column(name = "is_seen")
    private boolean isSeen;
}
