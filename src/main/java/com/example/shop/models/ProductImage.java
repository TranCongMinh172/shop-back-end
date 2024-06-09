package com.example.shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_imgage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String path;
}
