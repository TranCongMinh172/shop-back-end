package com.example.shop.service.interfaces;

import com.example.shop.exceptions.DataExistsException;
import com.example.shop.models.Color;
import com.example.shop.models.Product;
import com.example.shop.models.ProductDetail;
import com.example.shop.models.Size;

public interface ProductDetailService extends  BaseService<ProductDetail,Long>{
    void existsByColorAndSizeAndProduct(Color color, Size size, Product product) throws DataExistsException;
}
