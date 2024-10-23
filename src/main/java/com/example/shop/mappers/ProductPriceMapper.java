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
    public ProductPrice addProductPriceDtoToProductPrice(ProductPriceDto productPriceDto) throws DataNotFoundException {
        Product product = productService.findById(productPriceDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Double discountedPrice = product.getProductPrice() * productPriceDto.getDiscount();
        return ProductPrice
                .builder()
                .product(product)
                .discount(productPriceDto.getDiscount())
                .expiredDate(productPriceDto.getExpiryDate())
                .discountPrice(discountedPrice)
                .note(productPriceDto.getNote())
                .build();
    }
}
