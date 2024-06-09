package com.example.shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;
    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;
    private Integer quantity;
}
