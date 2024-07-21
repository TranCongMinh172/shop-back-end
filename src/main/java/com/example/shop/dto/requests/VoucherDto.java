package com.example.shop.dto.requests;

import com.example.shop.models.enums.ScopeType;
import com.example.shop.models.enums.VoucherType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Range(min = 0 , max = 1,message = "discount ....")
    private Double discount;

    @NotNull(message = "Voucher type must not be null")
    private VoucherType type;

    @NotNull(message = "Expired date must not be null")
    @Future(message = "Expired date must be in the future")
    private LocalDateTime expiredDate;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    private ScopeType scope;
}