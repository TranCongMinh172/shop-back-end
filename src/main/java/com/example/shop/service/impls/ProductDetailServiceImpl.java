package com.example.shop.service.impls;

import com.example.shop.exceptions.DataExistsException;
import com.example.shop.models.Color;
import com.example.shop.models.Product;
import com.example.shop.models.ProductDetail;
import com.example.shop.models.Size;
import com.example.shop.repositories.ProductDetailRepository;
import com.example.shop.repositories.ProductRepository;
import com.example.shop.service.interfaces.ProductDetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service

public class ProductDetailServiceImpl extends BaseServiceImpl<ProductDetail,Long> implements ProductDetailService {
    private ProductDetailRepository productDetailRepository;
    private ProductRepository productRepository;
    public ProductDetailServiceImpl(JpaRepository<ProductDetail, Long> repository) {
        super(repository);
    }
    @Autowired
    public void setProductDetailRepository(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductDetail save(ProductDetail productDetail) {
        Product product = productDetail.getProduct();
        int quantity = product.getTotalQuantity() != null ? product.getTotalQuantity() : 0;
        product.setTotalQuantity(quantity+productDetail.getQuantity());
        productRepository.save(product);
        return super.save(productDetail);
    }

    @Override
    public void existsByColorAndSizeAndProduct(Color color, Size size, Product product) throws DataExistsException {
        if(productDetailRepository.existsByColorAndSizeAndProduct(color,size,product)){
            throw new DataExistsException("product detail is exists");
        }
    }
}
