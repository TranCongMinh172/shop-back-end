package com.example.shop.repositories;

import com.example.shop.models.ProductImage;

import java.util.List;

public interface ProductImageRepository extends BaseRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}