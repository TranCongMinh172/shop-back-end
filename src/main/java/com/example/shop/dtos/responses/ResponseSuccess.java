package com.example.shop.dtos.requests.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class ResponseSuccess<T> {
    private  final  int status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  T data;

    public ResponseSuccess(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public ResponseSuccess(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
