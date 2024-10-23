package com.example.shop.controllers;



import com.example.shop.dtos.requests.CategoryDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.CategoryMapper;
import com.example.shop.models.Category;
import com.example.shop.dtos.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping()
    public ResponseSuccess<?> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        Category category  = categoryMapper.addCategoryDto(categoryDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a category successfully",
                categoryService.save(category)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseSuccess<?> deleteCategory(@PathVariable Long id){
        categoryService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "delete category with id "+id+" successfully"
        );
    }
    @PutMapping("/{id}")
    public ResponseSuccess<?> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto categoryDto){
        Category category = categoryMapper.addCategoryDto(categoryDto);
        category.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update category with id "+id+" successfully",
                categoryService.save(category)
        );
    }
    @GetMapping
    public ResponseSuccess<?> getAll(){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Successfully get all categories",
                categoryService.findAll()
        );
    }
    @GetMapping("/{id}")
    public ResponseSuccess<?> getById(@PathVariable Long id){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get category with id "+ id +" successfully",
                categoryService.findById(id)
        );
    }
    @PatchMapping("/{id}")
    public ResponseSuccess<?> updatePatchCategory(@PathVariable Long id, @RequestBody @Valid Map<String,?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update category with id "+ id +" successfully",
                categoryService.updatePatch(id,data)
        );
    }

}
