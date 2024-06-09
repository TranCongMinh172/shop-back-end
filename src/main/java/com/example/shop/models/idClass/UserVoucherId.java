package com.example.shop.models.idClass;

import com.example.shop.models.User;
import com.example.shop.models.Voucher;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class UserVoucherId implements Serializable {
    private User user;
    private Voucher voucher;

    public UserVoucherId(Long userId, Long voucherId) {
    }
}
