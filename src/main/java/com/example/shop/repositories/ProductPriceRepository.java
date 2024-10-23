package com.example.shop.repositories;

import com.example.shop.models.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPriceRepository extends BaseRepository<ProductPrice, Long> {
    List<ProductPrice> findAllByProductId(Long id);
}