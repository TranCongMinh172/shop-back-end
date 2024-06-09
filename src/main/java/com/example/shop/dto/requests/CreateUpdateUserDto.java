package com.example.shop.dto.requests;

import com.example.shop.dto.requests.CreateAddressDto;
import com.example.shop.models.enums.Gender;
import com.example.shop.models.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateUpdateUserDto {
    @NotBlank(message = "name must be not blank")
    private String userName;
    @NotBlank(message = "pass must be not blank")
    private String password;
    private Gender genders;
    @Pattern(message = "email is invalid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @NotBlank(message = "email must be not blank")
    private String email;
    @Pattern(message = "phone number is invalid", regexp = "0\\d{9}")
    @NotBlank(message = "phone must be not blank")
    private String phone;
    @Past(message = "current date must be greater than date of birth")
    private LocalDate dateOfBirth;
    private Role roles;
    private CreateAddressDto address;
}
