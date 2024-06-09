package com.example.shop.dto.requests.responses;

import java.util.List;

public record ResponseError(int status, List<String> errors) {
}
