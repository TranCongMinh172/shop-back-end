package com.example.shop.models.idClass;

import com.example.shop.models.Order;
import com.example.shop.models.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
public class OrderDetailId implements Serializable {
    private Order order;
    private ProductDetail productDetail;
}
