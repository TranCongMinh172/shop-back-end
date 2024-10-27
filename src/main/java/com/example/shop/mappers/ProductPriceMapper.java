package com.example.shop.mappers;

import com.example.shop.dtos.requests.ProductPriceDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Product;
import com.example.shop.models.ProductPrice;
import com.example.shop.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPriceMapper {
    private final ProductService productService;
    public ProductPrice productPriceDto2ProductPrice(ProductPriceDto productPriceDto)
            throws DataNotFoundException {
        Product product = productService.findById(productPriceDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Double discountedPrice = product.getPrice() * productPriceDto.getDiscount();
        Double discountedAmount = product.getPrice() - discountedPrice;
        return ProductPrice.builder()
                .product(product)
                .note(productPriceDto.getNote())
                .expiredDate(productPriceDto.getExpiryDate())
                .discount(productPriceDto.getDiscount())
                .discountedPrice(discountedPrice)
                .discountedAmount(discountedAmount)
                .build();
    }
}
