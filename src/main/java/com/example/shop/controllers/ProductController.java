package com.example.shop.controllers;


import com.example.shop.dto.requests.ProductDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.ProductMapper;
import com.example.shop.models.Product;
import com.example.shop.dto.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping()
    public ResponseSuccess<?> createProduct(@ModelAttribute @Valid ProductDto productDto)  throws Exception{
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a product successfully",
                productService.save(productDto)
        );
    }

    @GetMapping()
    public ResponseSuccess<?> getAll() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Successfully get all products.",
                productService.findAll()
        );
    }
    @GetMapping("/{id}")
    public ResponseSuccess<?> getProductById(@PathVariable Long id) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get product with id "+id+"successfully",
                productService.findById(id)
        );
    }
    @PutMapping("/{id}")
    public ResponseSuccess<?> updateProductById(@PathVariable Long id,@RequestBody @Valid  ProductDto productDto) throws DataNotFoundException {
        Product product = productMapper.updateProductDto2Product(id,productDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update product with id "+ id +" successfully",
                productService.save(product)
        );
    }
    @PatchMapping("/{id}")
    public ResponseSuccess<?> updateProduct(@PathVariable Long id, @RequestBody @Valid Map<String,?> data) throws DataNotFoundException {

        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update product with id "+ id +" successfully",
                productService.updatePatch(id,data)
        );
    }
}
