package com.example.shop.mappers;

import com.example.shop.dto.requests.SizeDto;
import com.example.shop.models.Size;
import org.springframework.stereotype.Component;

@Component
public class SizeMapper {
    public Size addSizeDto2Size(SizeDto sizeDto){
        return Size.builder()
                .numberSize(sizeDto.getNumberSize())
                .textSize(sizeDto.getTextSize())
                .sizeType(sizeDto.getSizeType())
                .build();
    }
}
