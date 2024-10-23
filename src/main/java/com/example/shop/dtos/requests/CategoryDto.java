package com.example.shop.dtos.requests;

import com.example.shop.models.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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