package com.example.shop.service.impls;

import com.example.shop.models.Color;
import com.example.shop.service.interfaces.BaseService;
import com.example.shop.service.interfaces.ColorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceimpl extends BaseServiceImpl<Color,Long> implements ColorService {
    public ColorServiceimpl(JpaRepository<Color, Long> repository) {
        super(repository);
    }
}
