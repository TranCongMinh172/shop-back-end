package com.example.shop.service.interfaces;

import com.example.shop.dtos.requests.ProductDto;
import com.example.shop.dtos.responses.PageResponse;
import com.example.shop.dtos.responses.product.ProductResponse;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Product;


public interface ProductService extends BaseService<Product, Long>{
    Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException;
    ProductResponse findProductById(Long id) throws DataNotFoundException;
    PageResponse<?> getProductForUserRole(int pageNo, int pageSize, String[] search, String[] sort);
    PageResponse<?> getProductSale(int pageNo, int pageSize, String[] search, String[] sort);
}
