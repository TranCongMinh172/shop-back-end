package com.example.shop.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDetailDto {
    @NotNull(message = "productId not null")
    private Long productId;
    @NotNull(message = "sizeId not null")
    private Long sizeId;
    @NotNull(message = "colorId not null")
    private Long colorId;
    @NotNull(message = "quantity not null")
    private Integer quantity;;

}
