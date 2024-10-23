package com.example.shop.mappers;

import com.example.shop.dtos.requests.CreateProviderDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Provider;
import com.example.shop.service.interfaces.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProviderMapper {
    private final AddressMapper addressMapper;
    private final ProviderService providerService;
    public Provider addProviderDto(CreateProviderDto createProviderDto){
        return Provider.builder()
                .providerName(createProviderDto.getProviderName())
                .phoneNumber(createProviderDto.getPhoneNumber())
                .email(createProviderDto.getEmail())
                .status(createProviderDto.getStatus())
                .address(addressMapper.addressDto2Address(createProviderDto.getCreateAddressDto())  )
                .build();
    }

    public Provider updateProviderDto(Long id,CreateProviderDto createProviderDto) throws DataNotFoundException {
        Provider provider = providerService.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Provider not found"));
            provider.setProviderName(createProviderDto.getProviderName());
            provider.setPhoneNumber(createProviderDto.getPhoneNumber());
            provider.setEmail(createProviderDto.getEmail());
            provider.setStatus(createProviderDto.getStatus());
            return provider;
    }
}
