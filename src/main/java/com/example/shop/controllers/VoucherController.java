package com.example.shop.controllers;

import com.example.shop.dto.requests.VoucherDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.VoucherMapper;
import com.example.shop.models.Voucher;
import com.example.shop.dto.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;
    private final VoucherMapper voucherMapper;

    @PostMapping()
    public ResponseSuccess<?> createVoucher(@RequestBody @Valid VoucherDto voucherDto) {
        Voucher voucher = voucherMapper.addVoucherDto(voucherDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a voucher successfully",
                voucherService.save(voucher)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseSuccess<?> deleteVoucher(@PathVariable Long id){
        voucherService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "delete voucher with id "+id+" successfully"
        );
    }
    @PutMapping("/{id}")
    public ResponseSuccess<?> updateVoucher(@PathVariable Long id, @RequestBody @Valid VoucherDto  voucherDto){
        Voucher voucher = voucherMapper.addVoucherDto(voucherDto);
        voucher.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update category with id "+id+" successfully",
                voucherService.save(voucher)
        );
    }
    @GetMapping
    public ResponseSuccess<?> getAll(){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Successfully get all categories",
                voucherService.findAll()
        );
    }
    @GetMapping("/{id}")
    public ResponseSuccess<?> getById(@PathVariable Long id){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get voucher with id "+ id +" successfully",
                voucherService.findById(id)
        );
    }
    @PatchMapping("/{id}")
    public ResponseSuccess<?> updatePatchVoucher(@PathVariable Long id, @RequestBody @Valid Map<String,?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update voucher with id "+ id +" successfully",
                voucherService.updatePatch(id,data)
        );
    }
}
