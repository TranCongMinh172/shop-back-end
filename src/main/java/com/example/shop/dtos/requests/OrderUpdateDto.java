package com.example.shop.dtos.requests;
import com.example.shop.models.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateDto {
    private OrderStatus status;
}
