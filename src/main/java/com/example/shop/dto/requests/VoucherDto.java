package com.example.shop.dto.requests;

import com.example.shop.models.enums.ScopeType;
import com.example.shop.models.enums.VoucherType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@Builder
public class VoucherDto  {
    @NotNull(message = "Max price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Max price must be greater than 0")
    private Double maxPrice;

    @NotNull(message = "Min price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Min price must be greater than 0")
    private Double minPrice;

    @NotNull(message = "Discount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount must be less than or equal to 100")
    private Double discount;

    @NotNull(message = "Voucher type must not be null")
    private VoucherType type;

    @NotNull(message = "Expired date must not be null")
    @Future(message = "Expired date must be in the future")
    private LocalDate expiredDate;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}