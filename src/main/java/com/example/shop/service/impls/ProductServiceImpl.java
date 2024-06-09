package com.example.shop.service.impls;

import com.example.shop.dto.requests.ProductDto;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.ProductMapper;
import com.example.shop.models.Product;
import com.example.shop.models.ProductImage;
import com.example.shop.repositories.ProductImageRepository;
import com.example.shop.repositories.ProductRepository;
import com.example.shop.service.interfaces.ProductService;
import com.example.shop.utils.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product,Long> implements ProductService {
    private ProductMapper productMapper;
    private ProductRepository productRepository;
    private ProductImageRepository productImageRepository;
    private S3Upload s3Upload;
    public ProductServiceImpl(JpaRepository<Product, Long> repository) {
        super(repository);
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Autowired
    public void setProductImageRepository(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }
    @Autowired
    public void setS3Upload(S3Upload s3Upload) {
        this.s3Upload = s3Upload;
    }

    @Override
    @Transactional(rollbackFor = {DataExistsException.class, DataNotFoundException.class})
    public Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException {
        if(productRepository.existsByProductName(productDto.getProductName())){
            throw new DataExistsException("Product already exists");
        }
        Product product = productMapper.addProductDto2Product(productDto);
        product = super.save(product);
        if(!productDto.getImages().isEmpty()){
            List<MultipartFile> files = productDto.getImages();
            for(MultipartFile file : files){
                if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")){
                    throw new DataExistsException("Image not supported");
                }
                try {
                    String path = s3Upload.uploadFile(file);
                    ProductImage productImage = new ProductImage();
                    productImage.setProduct(product);
                    productImage.setPath(path);
                    if(productDto.getThumbnail() != null &&
                    productDto.getThumbnail() == files.indexOf(file)){
                        product.setThumbnail(path);
                    }
                    productImageRepository.save(productImage);
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return super.save(product);
    }

}
