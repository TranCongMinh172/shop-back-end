package com.example.shop.dtos.requests.responses;

import java.util.List;

public record ResponseError(int status, List<String> errors) {
}
