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
    @Column(name = "media_id")
    private Long id;
    private String path;
    private MediaType type;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
