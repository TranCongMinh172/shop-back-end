package com.example.shop.mappers;

import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Category;
import com.example.shop.models.Product;
import com.example.shop.dtos.requests.ProductDto;
import com.example.shop.models.Provider;
import com.example.shop.models.enums.Status;
import com.example.shop.repositories.ProductRepository;
import com.example.shop.service.interfaces.CategoryService;
import com.example.shop.service.interfaces.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProviderService providerService;
    public Product addProductDto2Product(ProductDto productDto) throws DataNotFoundException {
        Category category = categoryService.findById(productDto.getCategoryId())
                .orElseThrow(()-> new DataNotFoundException("category not found"));
        Provider provider = providerService.findById(productDto.getProviderId())
                .orElseThrow(()-> new DataNotFoundException("provider not found"));
        return Product.builder()
                .productName(productDto.getProductName())
                .price(productDto.getProductPrice())
                .description(productDto.getProductDescription())
                .productStatus(Status.ACTIVE)
                .category(category)
                .provider(provider)
                .build();
    }
    public Product updateProductDto2Product(Long id,ProductDto productDto) throws DataNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("product not found"));
                product.setProductName(productDto.getProductName());
                product.setPrice(productDto.getProductPrice());
                product.setDescription(productDto.getProductDescription());
                product.setProductStatus(Status.ACTIVE);
                return product;

    }
}
