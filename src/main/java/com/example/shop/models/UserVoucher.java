package com.example.shop.models;

import com.example.shop.models.idClass.UserVoucherId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_voucher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserVoucherId.class)
public class UserVoucher {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    private boolean isVoucherUsed;
}
