package com.example.shop.service.impls;

import com.example.shop.dtos.requests.ProductDto;
import com.example.shop.dtos.responses.PageResponse;
import com.example.shop.dtos.responses.product.ProductResponse;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.ProductMapper;
import com.example.shop.models.Product;
import com.example.shop.models.ProductDetail;
import com.example.shop.models.ProductImage;
import com.example.shop.repositories.BaseRepository;
import com.example.shop.repositories.ProductDetailRepository;
import com.example.shop.repositories.ProductImageRepository;
import com.example.shop.repositories.ProductRepository;
import com.example.shop.repositories.customizations.ProductQuery;
import com.example.shop.service.interfaces.ProductService;
import com.example.shop.utils.S3Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
@Slf4j
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product,Long> implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final S3Upload s3Upload;
    private final ProductDetailRepository productDetailRepository;
    private final ProductQuery productQuery;
    public ProductServiceImpl(BaseRepository<Product, Long> repository, ProductMapper productMapper,
                              ProductRepository productRepository,
                              ProductImageRepository productImageRepository,
                              S3Upload s3Upload,
                              ProductDetailRepository productDetailRepository,
                              ProductQuery productQuery) {
        super(repository, Product.class);
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.s3Upload = s3Upload;
        this.productDetailRepository = productDetailRepository;
        this.productQuery = productQuery;
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

    @Override
    public ProductResponse findProductById(Long id) throws DataNotFoundException{
        Product product = super.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product not found"));
        List<ProductDetail> productDetails = productDetailRepository.findByProductId(id);
        List<ProductImage> productImages = productImageRepository.findByProductId(id);
        return ProductResponse.builder()
                .product(product)
                .productDetails(productDetails)
                .productImages(productImages)
                .build();
    }

    @Override
    public PageResponse<?> getProductForUserRole(int pageNo, int pageSize, String[] search, String[] sort) {
        return productQuery.getPageData(pageNo, pageSize, search, sort);
    }

    @Override
    public PageResponse<?> getProductSale(int pageNo, int pageSize, String[] search, String[] sort) {
        return productQuery.getPageDataPromotion(pageNo, pageSize, search, sort);
    }

}
