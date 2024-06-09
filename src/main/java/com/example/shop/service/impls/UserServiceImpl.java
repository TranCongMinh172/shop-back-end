package com.example.shop.service.impls;

import com.example.shop.dto.requests.CreateUpdateUserDto;
import com.example.shop.models.User;
import com.example.shop.service.interfaces.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
    public UserServiceImpl(JpaRepository<User, Long> repository) {
        super(repository);
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


}
