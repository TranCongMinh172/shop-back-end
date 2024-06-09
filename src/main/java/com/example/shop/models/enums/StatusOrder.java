package com.example.shop.models.enums;

public enum StatusOrder {
    PENDING,     // Đơn hàng đang chờ xử lý
    PROCESSING,  // Đơn hàng đang được xử lý
    SHIPPED,     // Đơn hàng đã được giao
    DELIVERED,   // Đơn hàng đã được nhận
    CANCELLED    // Đơn hàng đã bị hủy
}
