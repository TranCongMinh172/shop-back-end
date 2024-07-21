package com.example.shop.repositories;

import com.example.shop.models.Color;
import com.example.shop.models.Product;
import com.example.shop.models.ProductDetail;
import com.example.shop.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    boolean existsByColorAndSizeAndProduct(Color color, Size size, Product product);
    List<ProductDetail> findByProductId(Long productId);
}