package com.example.shop.mappers;

import com.example.shop.dtos.requests.CreateUpdateUserDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.User;
import com.example.shop.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final AddressMapper addressMapper;
    private final UserService userService;
    public User addUserDto2User(CreateUpdateUserDto createUpdateUserDto){
        return User.builder()
                .name(createUpdateUserDto.getUserName())
                .phoneNumber(createUpdateUserDto.getPhone())
                .email(createUpdateUserDto.getEmail())
                .password(createUpdateUserDto.getPassword())
                .gender(createUpdateUserDto.getGenders())
                .dateOfBirth(createUpdateUserDto.getDateOfBirth())
                .role(createUpdateUserDto.getRoles())
                .address(addressMapper.addressDto2Address(createUpdateUserDto.getAddress()))
                .build();
    }
    public User updateUserDto2User(Long id,CreateUpdateUserDto createUpdateUserDto) throws DataNotFoundException {
        User user = userService.findById(id).orElseThrow(()-> new DataNotFoundException("User not found"));
            user.setName(createUpdateUserDto.getUserName());
            user.setPhoneNumber(createUpdateUserDto.getPhone());
            user.setEmail(createUpdateUserDto.getEmail());
            user.setPassword(createUpdateUserDto.getPassword());
            user.setGender(createUpdateUserDto.getGenders());
            user.setDateOfBirth(createUpdateUserDto.getDateOfBirth());
            user.setRole(createUpdateUserDto.getRoles());
        return user;
    }

}
