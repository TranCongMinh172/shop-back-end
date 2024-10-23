package com.example.shop.service.impls;


import com.example.shop.models.Category;
import com.example.shop.repositories.BaseRepository;
import com.example.shop.service.interfaces.CategoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {

    public CategoryServiceImpl(BaseRepository<Category, Long> repository) {
        super(repository, Category.class);
    }

}
