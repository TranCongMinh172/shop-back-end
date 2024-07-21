package com.example.shop.models.idClass;

import com.example.shop.models.Order;
import com.example.shop.models.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class OrderDetailId implements Serializable {
    private Order order;
    private ProductDetail productDetail;
}
