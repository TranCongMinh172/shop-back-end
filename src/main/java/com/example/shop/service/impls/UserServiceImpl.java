package com.example.shop.service.impls;

import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.User;
import com.example.shop.models.UserDetail;
import com.example.shop.repositories.BaseRepository;
import com.example.shop.repositories.UserRepository;
import com.example.shop.service.interfaces.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(BaseRepository<User, Long> repository, UserRepository userRepository) {
        super(repository, User.class);
        this.userRepository = userRepository;
    }


    @Override
    public Map<String, Object> extractAddressData(Map<String, ?> data) {
        Map<String, Object> addressData = new HashMap<>();
        Set<String> addressFields = Set.of("street", "city", "district"); // Thêm các trường của Address vào đây

        for (String key : data.keySet()) {
            if (addressFields.contains(key)) {
                addressData.put(key, data.get(key));
            }
        }
        // Loại bỏ các trường liên quan đến địa chỉ khỏi dữ liệu đầu vào
        data.keySet().removeAll(addressFields);

        return addressData;
    }

    @Override
    public User findByEmail(String email) throws DataNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("user not found"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Email not found"));
        return new UserDetail(user);
    }
}
