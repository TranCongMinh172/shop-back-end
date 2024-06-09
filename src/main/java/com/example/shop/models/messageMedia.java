package com.example.shop.models;

import com.example.shop.models.enums.MediaTpe;
import com.example.shop.models.enums.MediaType;
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
@Table(name = "message_media")
public class messageMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_media_id")
    private Long id;
    private String path;
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;
}
