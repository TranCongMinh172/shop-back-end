package com.example.shop.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductOrderDto {
    @NotNull(message = "product id must be not null")
    private Long productDetailId;
    @NotNull(message = "quantity must be not null")
    private Integer quantity;
}
