package com.example.shop.service.impls;

import com.example.shop.models.Address;
import com.example.shop.service.interfaces.AddressService;
import com.example.shop.service.interfaces.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address,Long> implements AddressService {
    public AddressServiceImpl(JpaRepository<Address, Long> repository) {
        super(repository);
    }

}
