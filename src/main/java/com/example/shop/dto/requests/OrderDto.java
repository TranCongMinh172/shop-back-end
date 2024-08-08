package com.example.shop.dto.requests;

import com.example.shop.models.enums.DeliveryMethod;
import com.example.shop.models.enums.PaymentMethod;
import com.example.shop.models.enums.StatusOrder;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDto {
//    @NotNull(message = "Order date must not be null")
//    private Date orderDate;

//    @NotNull(message = "Status must not be null")
//    private StatusOrder status;
    @Size(max = 255, message = "Note must be less than 255 characters")
    private String note;
    @NotBlank(message = "Phone must not be blank")
    @Pattern(regexp = "0[0-9]{9}", message = "Phone must be 10 digits and start with 0")
    private String phone;
    @NotBlank(message = "Buyer name must not be blank")
    private String buyerName;
    @NotNull(message = "Payment method must not be null")
    private PaymentMethod paymentMethod;
    @NotNull(message = "Delivery method must not be null")
    private DeliveryMethod deliveryMethod;
    private Long userId;
    private String email;
    private CreateAddressDto createAddressDto;
    private List<ProductOrderDto> productOrderDtos;
    private List<Long> vouchers;
}