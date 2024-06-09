package com.example.shop.dto.requests;

import com.example.shop.models.Size;
import com.example.shop.models.enums.SizeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Size}
 */
@Getter
@Setter
@Builder
public class SizeDto {
    @PositiveOrZero(message = "Number size must be zero or positive")
    private Integer numberSize;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Text size must only contain letters")
    private String textSize;
    @NotNull(message = "Size type must not be null")
    private SizeType sizeType;
}