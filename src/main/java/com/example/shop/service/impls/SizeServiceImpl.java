package com.example.shop.service.impls;

import com.example.shop.models.Size;
import com.example.shop.service.interfaces.SizeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl extends BaseServiceImpl<Size, Long> implements SizeService {
    public SizeServiceImpl(JpaRepository<Size, Long> repository) {
        super(repository);
    }
}
