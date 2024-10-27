package com.example.shop.controllers;


import com.example.shop.dtos.requests.ChangePasswordRequest;
import com.example.shop.dtos.requests.CreateUpdateUserDto;
import com.example.shop.dtos.requests.UserUpdateDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.UserMapper;
import com.example.shop.models.User;
import com.example.shop.dtos.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.AddressService;
import com.example.shop.service.interfaces.UserService;
import com.example.shop.utils.S3Upload;
import com.example.shop.utils.ValidToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper usermapper;
    private final AddressService addressService;
    private final ValidToken validToken;
    private final S3Upload s3Upload;

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
                userService.getUserByEmail(email)
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

    @PutMapping("/{email}")
    public ResponseSuccess<?> updateUser(@PathVariable String email, @RequestBody UserUpdateDto userDto, HttpServletRequest request)
            throws Exception {
        validToken.valid(email, request);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated user",
                userService.updateUser(email, userDto)
        );
    }
    @PostMapping("/change-password")
    public ResponseSuccess<?> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest, HttpServletRequest request)
            throws Exception {
        validToken.valid(changePasswordRequest.getEmail(), request);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "change password successfully",
                userService.changePassword(changePasswordRequest)
        );
    }

    @PostMapping("/upload")
    public ResponseSuccess<?> uploadAvatar(@RequestParam("avatar") MultipartFile file) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                s3Upload.uploadFile(file)
        );
    }
}
