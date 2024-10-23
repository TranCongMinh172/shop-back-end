package com.example.shop.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateAddressDto {
    @NotBlank(message = "street must be not blank")
    private String street;
    @NotBlank(message = "district must be not blank")
    private String city;
    @NotBlank(message = "district must be not blank")
    private String district;
}
