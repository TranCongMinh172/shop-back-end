package com.example.shop.controllers;

import com.example.shop.dtos.requests.ProductPriceDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.ProductPriceMapper;
import com.example.shop.service.interfaces.ProductPriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shop.dtos.requests.responses.ResponseSuccess;

@RestController
@RequestMapping("/api/v1/productPrices")
@RequiredArgsConstructor
public class ProductPriceController {
    private final ProductPriceService productPriceService;
    private final ProductPriceMapper productPriceMapper;
    @PostMapping
    public ResponseSuccess<?> addProductPrice(@RequestBody @Valid ProductPriceDto productPriceDto) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create a product price successfully",
                productPriceService.save(productPriceMapper.addProductPriceDtoToProductPrice(productPriceDto))
        );
    }
}
