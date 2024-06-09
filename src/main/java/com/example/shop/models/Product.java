package com.example.shop.models;

import com.example.shop.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product  extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String thumbnail;
    private Float avgRating;
    private Integer totalQuantity;
    private Status status;
    private Integer numberOfRating;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider  provider;
}
