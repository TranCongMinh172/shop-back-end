package com.example.shop.service.interfaces;


import com.example.shop.dtos.requests.ChangePasswordRequest;
import com.example.shop.dtos.requests.UserUpdateDto;
import com.example.shop.dtos.responses.LoginResponse;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.User;
public interface UserService extends BaseService<User, Long> {
    LoginResponse changePassword(ChangePasswordRequest changePasswordRequest) throws DataNotFoundException;
    User getUserByEmail(String email) throws DataNotFoundException;
    User updateUser(String email, UserUpdateDto userUpdateDto) throws DataNotFoundException;
}