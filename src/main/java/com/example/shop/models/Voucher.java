package com.example.shop.models;

import com.example.shop.models.enums.Scope;
import com.example.shop.models.enums.VoucherType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Long id;
    @Column(name = "max_price", columnDefinition = "decimal(10,2)")
    private Double maxPrice;
    @Column(name = "min_amount", columnDefinition = "decimal(10,2)")
    private Double minAmount;
    private Float discount;
    @Enumerated(EnumType.STRING)
    @Column(name = "voucher_type")
    private VoucherType voucherType;
    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredDate;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Scope scope;
}
