package com.example.shop.controllers;

import com.example.shop.dto.requests.CreateAddressDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.AddressMapper;
import com.example.shop.models.Address;
import com.example.shop.dto.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final AddressMapper addressmapper;

    @PostMapping()
    public ResponseSuccess<?> createAddress(@RequestBody @Valid CreateAddressDto createAddressDto) {
        Address address  = addressmapper.addressDto2Address(createAddressDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a address successfully",
                addressService.save(address)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseSuccess<?> deleteAddress(@PathVariable Long id){
        addressService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "delete address with id "+id+" successfully"
        );
    }
    @PutMapping("/{id}")
    public ResponseSuccess<?> updateAddress(@PathVariable Long id, @RequestBody @Valid CreateAddressDto createAddressDto){
        Address address = addressmapper.addressDto2Address(createAddressDto);
        address.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update address with id "+id+" successfully",
                addressService.save(address)
        );
    }
    @PatchMapping("/{id}")
        public ResponseSuccess<?> updatePatchAddress(@PathVariable Long id, @RequestBody @Valid Map<String,?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update address with id "+ id +" successfully",
                addressService.updatePatch(id,data)
        );
    }

}
