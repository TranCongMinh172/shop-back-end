package com.example.shop.service.interfaces;

import com.example.shop.dtos.requests.ProductDto;
import com.example.shop.dtos.responses.PageResponse;
import com.example.shop.dtos.responses.product.ProductResponse;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface ProductService extends BaseService<Product, Long> {
    Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException;
    ProductResponse findProductById(Long id) throws DataNotFoundException;
    PageResponse<?> getProductsForUserRole(int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException;
    PageResponse<?> getProductsSale(int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException;
}
