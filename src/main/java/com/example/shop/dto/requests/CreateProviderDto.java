package com.example.shop.dto.requests;

import com.example.shop.dto.requests.CreateAddressDto;
import com.example.shop.models.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProviderDto {
    @NotBlank(message = "name must be not blank")
    private String providerName;
    @Pattern(message = "phone number is invalid", regexp = "0\\d{9}")
    @NotBlank(message = "phone must be not blank")
    private String phoneNumber;
    @Pattern(message = "email is invalid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @NotBlank(message = "email must be not blank")
    private String email;
    private Status status;
    private CreateAddressDto createAddressDto;
}
