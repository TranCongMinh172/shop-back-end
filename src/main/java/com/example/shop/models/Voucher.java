package com.example.shop.models;

import com.example.shop.models.enums.ScopeType;
import com.example.shop.models.enums.VoucherType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private Double maxPrice;
    private Double minAmount;
    private Double discount;
    private VoucherType type;
    private LocalDateTime expiredDate;
    private Integer quantity;
    private ScopeType scope;
}
