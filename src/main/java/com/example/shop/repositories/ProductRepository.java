package com.example.shop.repositories;

import com.example.shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends BaseRepository<Product, Long> {
    boolean existsByProductName(String productName);
}