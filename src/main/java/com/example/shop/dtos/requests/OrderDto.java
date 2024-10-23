package com.example.shop.dtos.requests;

import com.example.shop.models.enums.DeliveryMethod;
import com.example.shop.models.enums.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    @NotNull(message = "user id must be not null")
    private String email;
    @NotNull(message = "payment method must be not null")
    private PaymentMethod paymentMethod;
    private String note;
    @NotNull(message = "address must be not null")
    private CreateAddressDto address;
    @Pattern(message = "phone number is invalid", regexp = "^0\\d{9}")
    @NotBlank(message = "phone number must be not blank")
    private String phoneNumber;
    @NotBlank(message = "buyer name must be not blank")
    private String buyerName;
    @NotNull(message = "delivery method must be not null")
    private DeliveryMethod deliveryMethod;
    @NotEmpty(message = "products order must be not empty")
    private List<ProductOrderDto> productOrders;
    private List<Long> vouchers;

}