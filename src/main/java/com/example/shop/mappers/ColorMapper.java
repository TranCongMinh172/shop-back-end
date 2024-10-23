package com.example.shop.mappers;

import com.example.shop.dtos.requests.ColorDto;
import com.example.shop.models.Color;
import com.example.shop.service.interfaces.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ColorMapper {
    private final ColorService colorService;
    public Color addColorDto2ColorDto(ColorDto colorDto) {
        Color color = new Color();
        color.setName(colorDto.getColorName());
        return  colorService.save(color);
    }
}
