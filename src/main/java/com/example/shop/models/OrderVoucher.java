package com.example.shop.models;

import com.example.shop.models.idClass.OrderVoucherId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_vouchers")
@IdClass(OrderVoucherId.class)
public class OrderVoucher {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Voucher voucher;
    @Id
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Order order;
}
