package com.example.shop.controllers;

import com.example.shop.dtos.requests.ProductDetailDto;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.ProductDetailMapper;
import com.example.shop.models.ProductDetail;
import com.example.shop.service.interfaces.ProductDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.shop.dtos.requests.responses.ResponseSuccess;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/productDetails")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;
    private final ProductDetailMapper productDetailMapper;

    @PostMapping
    public ResponseSuccess<?> addProductDetail(@RequestBody @Valid ProductDetailDto productDetailDto) throws DataNotFoundException, DataExistsException {
        ProductDetail productDetail = productDetailMapper.addProductDetailDto2ProductDetail(productDetailDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create a product detail successfully",
                productDetailService.save(productDetail)
        );
    }

    @GetMapping
    public ResponseSuccess<?> getAll()  {

        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get all product details successfully",
                productDetailService.findAll()
        );
    }
    @GetMapping("/{id}")
    public ResponseSuccess<?> getById(@PathVariable Long id)  {

        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get product detail "+id+" successfully",
                productDetailService.findById(id)
        );
    }
    @PatchMapping("/{id}")
    public ResponseSuccess<?> updatePatchProductDetail(@PathVariable Long id, @RequestBody Map<String,?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update product detail "+id+" successfully",
                productDetailService.updatePatch(id,data)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseSuccess<?> deleteById(@PathVariable Long id)  {
        productDetailService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "delete product detail "+id+" successfully"
        );
    }
}
