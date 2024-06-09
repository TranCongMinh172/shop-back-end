package com.example.shop.mappers;

import com.example.shop.dto.requests.CreateUpdateUserDto;
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
                .userName(createUpdateUserDto.getUserName())
                .phone(createUpdateUserDto.getPhone())
                .email(createUpdateUserDto.getEmail())
                .password(createUpdateUserDto.getPassword())
                .genders(createUpdateUserDto.getGenders())
                .dateOfBirth(createUpdateUserDto.getDateOfBirth())
                .roles(createUpdateUserDto.getRoles())
                .address(addressMapper.addressDto2Address(createUpdateUserDto.getAddress()))
                .build();
    }
    public User updateUserDto2User(Long id,CreateUpdateUserDto createUpdateUserDto) throws DataNotFoundException {
        User user = userService.findById(id).orElseThrow(()-> new DataNotFoundException("User not found"));
            user.setUserName(createUpdateUserDto.getUserName());
            user.setPhone(createUpdateUserDto.getPhone());
            user.setEmail(createUpdateUserDto.getEmail());
            user.setPassword(createUpdateUserDto.getPassword());
            user.setGenders(createUpdateUserDto.getGenders());
            user.setDateOfBirth(createUpdateUserDto.getDateOfBirth());
            user.setRoles(createUpdateUserDto.getRoles());
        return user;
    }

}
