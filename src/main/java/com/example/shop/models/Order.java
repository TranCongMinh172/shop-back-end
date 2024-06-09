package com.example.shop.models;

import com.example.shop.models.enums.DeliveryMethod;
import com.example.shop.models.enums.PaymentMethod;
import com.example.shop.models.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseModel {
    @Id
    @Column(name = "order_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Date orderDate;
    private StatusOrder status;
    private String note;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private String phone;
    private String buyerName;
    private PaymentMethod paymentMethod;
    private DeliveryMethod deliveryMethod;
    private Double original_amount;
    private Double discountedPrice;
    private Double discountedAmount;
    private Double deliveryAmount;

}
