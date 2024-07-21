package com.example.shop.dto.responses;

import com.example.shop.models.Product;
import com.example.shop.models.ProductDetail;
import com.example.shop.models.ProductImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Product product;
    private List<ProductDetail> productDetails;
    private List<ProductImage> productImages;
}
