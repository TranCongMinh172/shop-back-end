package com.example.shop.service.interfaces;


import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.Map;

public interface UserService extends BaseService<User,Long> , UserDetailsService {
    Map<String, Object> extractAddressData(Map<String, ?> data);

    User findByEmail(String email) throws DataNotFoundException;
}
