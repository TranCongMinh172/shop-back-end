package com.example.shop.models;

import com.example.shop.models.enums.Gender;
import com.example.shop.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends  BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id ")
    private Long userId;
    private String userName;
    private String password;
    private Gender genders;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private Role roles;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    private boolean isVerify;
    @Column(name = "otp")
    private String otp;
    private String otpResetPassword;
}
