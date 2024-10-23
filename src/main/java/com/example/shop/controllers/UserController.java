package com.example.shop.controllers;


import com.example.shop.dtos.requests.CreateUpdateUserDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.UserMapper;
import com.example.shop.models.User;
import com.example.shop.dtos.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.AddressService;
import com.example.shop.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper usermapper;
    private final AddressService addressService;
    @PostMapping()
    public ResponseSuccess<?> createUser(@RequestBody @Valid  CreateUpdateUserDto createUpdateUserDto) throws DataNotFoundException {
        User user = usermapper.addUserDto2User(createUpdateUserDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a user successfully",
                userService.save(user)
        );
    }

    @GetMapping()
    public ResponseSuccess<?> getAllUsers() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Successfully get all users.",
                userService.findAll()
        );
    }
    @GetMapping("/{email}")
    public ResponseSuccess<?> getUserByEmail( @PathVariable String email) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Successfully get user.",
                userService.findByEmail(email)
        );
    }
    @PutMapping("/{id}")
    public ResponseSuccess<?> getUserById(@PathVariable Long id,@RequestBody @Valid CreateUpdateUserDto createUpdateUserDto) throws DataNotFoundException {
        User user = usermapper.updateUserDto2User(id,createUpdateUserDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update user with id "+ id +" successfully",
                userService.save(user)
        );
    }
    @PatchMapping("/{id}")
    public ResponseSuccess<?> updateUser(@PathVariable Long id, @RequestBody @Valid  Map<String,?> data) throws DataNotFoundException {
        User user = userService.findById(id).orElseThrow(()-> new DataNotFoundException("user not found"));
        Map<String,Object> addressData = userService.extractAddressData(data);
        if (!addressData.isEmpty()) {
            addressService.updatePatch(user.getAddress().getId(), addressData);
        }else {
            userService.updatePatch(id, data);
        }
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update user with id "+ id +" successfully",
                userService.findById(id)
        );
    }

}
