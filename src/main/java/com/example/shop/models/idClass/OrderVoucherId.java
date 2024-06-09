package com.example.shop.models.idClass;

import com.example.shop.models.Order;
import com.example.shop.models.Voucher;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
public class OrderVoucherId implements Serializable {
    private Order order;
    private Voucher voucher;
}
