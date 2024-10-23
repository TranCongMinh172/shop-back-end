package com.example.shop.mappers;

import com.example.shop.dtos.requests.ProductDetailDto;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Color;
import com.example.shop.models.Product;
import com.example.shop.models.ProductDetail;
import com.example.shop.models.Size;
import com.example.shop.service.interfaces.ColorService;
import com.example.shop.service.interfaces.ProductDetailService;
import com.example.shop.service.interfaces.ProductService;
import com.example.shop.service.interfaces.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDetailMapper {
    private final ProductService productService;
    private final SizeService sizeService;
    private final ColorService colorService;
    private final ProductDetailService productDetailService;
    public ProductDetail addProductDetailDto2ProductDetail(ProductDetailDto productDetailDto) throws DataNotFoundException, DataExistsException {
        Product product = productService.findById(productDetailDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Size size = sizeService.findById(productDetailDto.getSizeId())
                .orElseThrow(() -> new DataNotFoundException("Size not found"));
        Color color = colorService.findById(productDetailDto.getColorId())
                .orElseThrow(() -> new DataNotFoundException("Color not found"));
        productDetailService.existsByColorAndSizeAndProduct(color,size,product);
        return ProductDetail
                .builder()
                .product(product)
                .size(size)
                .color(color)
                .quantity(productDetailDto.getQuantity())
                .build();
    }
}
