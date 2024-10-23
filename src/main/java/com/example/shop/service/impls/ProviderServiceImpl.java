package com.example.shop.service.impls;


import com.example.shop.models.Provider;

import com.example.shop.repositories.BaseRepository;
import com.example.shop.service.interfaces.ProviderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service

public class ProviderServiceImpl extends BaseServiceImpl<Provider, Long> implements ProviderService {

    public ProviderServiceImpl(BaseRepository<Provider, Long> repository) {
        super(repository,Provider.class);
    }
}
