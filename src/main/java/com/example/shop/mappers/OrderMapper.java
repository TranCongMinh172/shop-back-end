package com.example.shop.mappers;

import com.example.shop.dto.requests.OrderDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Address;
import com.example.shop.models.Order;
import com.example.shop.models.User;
import com.example.shop.service.interfaces.OrderService;
import com.example.shop.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderService orderService;
    private final AddressMapper addressMapper;
    private final   UserMapper userMapper;
    private final UserService  userService;

    public Order addOrderDto2Order(OrderDto orderDto) throws DataNotFoundException {
        Address address;
        User user = userService.findById(orderDto.getUserId())
                .orElseThrow(()-> new DataNotFoundException("user not found"));
        if(orderDto.getCreateAddressDto()!=null){
            address  = addressMapper.addressDto2Address(orderDto.getCreateAddressDto());
        }else {
            address = user.getAddress();
        }
        return Order.builder()
                .address(address)
                .note(orderDto.getNote())
                .buyerName(orderDto.getBuyerName())
                .phone(orderDto.getPhone())
                .paymentMethod(orderDto.getPaymentMethod())
                .deliveryMethod(orderDto.getDeliveryMethod())
                .user(user)
                .build();
    }
}
