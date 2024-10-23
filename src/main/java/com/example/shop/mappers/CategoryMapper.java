package com.example.shop.mappers;

import com.example.shop.dtos.requests.CategoryDto;
import com.example.shop.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category addCategoryDto(CategoryDto categoryDto){
        return Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .status(categoryDto.getStatus())
                .build();
    }
}
