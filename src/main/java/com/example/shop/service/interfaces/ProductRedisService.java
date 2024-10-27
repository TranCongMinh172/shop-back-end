package com.example.shop.service.interfaces;


import com.example.shop.dtos.responses.PageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProductRedisService {
    PageResponse<?> getProductsInCache(int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException;
    void saveProductsInCache(PageResponse<?> products, int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException;
    void clearCache();
}
