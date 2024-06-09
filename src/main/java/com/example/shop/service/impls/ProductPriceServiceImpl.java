package com.example.shop.service.impls;

import com.example.shop.models.ProductPrice;
import com.example.shop.service.interfaces.ProductPriceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceServiceImpl extends BaseServiceImpl<ProductPrice,Long> implements ProductPriceService {
    public ProductPriceServiceImpl(JpaRepository<ProductPrice, Long> repository) {
        super(repository);
    }
}
