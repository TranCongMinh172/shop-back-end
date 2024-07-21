package com.example.shop.dto.requests;

import com.example.shop.models.Product;
import com.example.shop.models.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * DTO for {@link Product}
 */
@Getter
@Setter
@Builder
public class ProductDto  {
    @NotBlank(message = "Product name must not be blank")
    private String productName;
    private String productDescription;
    @NotNull(message = "Product price must not be null")
    private Double productPrice;
    private Integer thumbnail;
    @NotNull(message = "Product status must not be null")
    private Long  categoryId;
    private Long  providerId;
    private List<MultipartFile> images;
}