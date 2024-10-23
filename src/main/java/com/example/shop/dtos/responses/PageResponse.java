package com.example.shop.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private int pageNo;
    private long totalPage;
    private int totalElement;
    private T data;
}
