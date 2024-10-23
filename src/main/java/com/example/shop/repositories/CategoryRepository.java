package com.example.shop.repositories;

import com.example.shop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends BaseRepository<Category, Long> {
}