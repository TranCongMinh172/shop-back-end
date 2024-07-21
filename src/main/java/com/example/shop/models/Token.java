package com.example.shop.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiredDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
