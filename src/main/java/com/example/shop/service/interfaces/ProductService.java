package com.example.shop.service.interfaces;

import com.example.shop.dto.requests.ProductDto;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Product;


public interface ProductService extends BaseService<Product, Long>{
    Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException;
}
