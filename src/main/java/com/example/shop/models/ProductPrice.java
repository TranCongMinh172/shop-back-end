package com.example.shop.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "product_price")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_price_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private Float discount;
    private Double discountPrice;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiredDate;
    private String note;
}
