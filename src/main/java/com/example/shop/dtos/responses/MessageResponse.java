package com.example.shop.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse<T> {
    private T data;
    private String type;
}
