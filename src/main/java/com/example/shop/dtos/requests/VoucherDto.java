package com.example.shop.dtos.requests;

import com.example.shop.models.enums.Scope;
import com.example.shop.models.enums.VoucherType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class VoucherDto  {
    @NotNull(message = "max price must be not null")
    private Double maxPrice;
    @NotNull(message = "min amount must be not null")
    private Double minAmount;
    @NotNull(message = "discount must be not null")
    private Float discount;
    private VoucherType voucherType;
    @NotNull(message = "expired date must be not null")
    @Future(message = "expired date must be greater than current date")
    private LocalDateTime expiredDate;
    private Integer quantity;
    @NotNull(message = "scope must be not null")
    private Scope scope;
}