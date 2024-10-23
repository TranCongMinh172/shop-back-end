package com.example.shop.repositories;

import com.example.shop.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends BaseRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}