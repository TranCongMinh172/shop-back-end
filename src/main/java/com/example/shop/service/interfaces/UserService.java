package com.example.shop.service.interfaces;

import com.example.shop.dto.requests.CreateUpdateUserDto;
import com.example.shop.models.User;

import java.util.Map;

public interface UserService extends BaseService<User,Long> {
    Map<String, Object> extractAddressData(Map<String, ?> data);
}
