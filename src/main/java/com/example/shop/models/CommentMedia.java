package com.example.shop.models;

import com.example.shop.models.enums.MediaType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment_media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_media_id")
    private Long id;
    private String path;
    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private MediaType mediaType;
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
}
