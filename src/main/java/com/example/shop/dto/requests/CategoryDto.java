package com.example.shop.dto.requests;

import com.example.shop.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.shop.models.Category}
 */
@Getter
@Setter
@Builder
public class CategoryDto {
    @NotBlank(message = "name must be not blank")
    private String categoryName;
    private Status status;
//    @JsonCreator
//    public CategoryDto(@JsonProperty("categoryName") String categoryName) {
//        this.categoryName = categoryName;
//    }
}