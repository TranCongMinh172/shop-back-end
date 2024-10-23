package com.example.shop.mappers;

import com.example.shop.dtos.requests.CreateAddressDto;
import com.example.shop.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address addressDto2Address(CreateAddressDto createAddressDto){
           return Address.builder()
                   .street(createAddressDto.getStreet())
                   .city(createAddressDto.getCity())
                   .district(createAddressDto.getDistrict())
                   .build();
    }

}
