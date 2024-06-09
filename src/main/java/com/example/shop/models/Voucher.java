package com.example.shop.models;

import com.example.shop.models.enums.VoucherType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private Double minPrice;
    private Double discount;
    private VoucherType type;
    private LocalDate expiredDate;
    private Integer quantity;
}
