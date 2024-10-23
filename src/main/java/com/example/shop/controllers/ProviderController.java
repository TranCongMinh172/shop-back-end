package com.example.shop.controllers;

import com.example.shop.dtos.requests.CreateProviderDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.ProviderMapper;
import com.example.shop.models.Provider;
import com.example.shop.dtos.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;
    private final ProviderMapper providerMapper;

    @PostMapping()
    public ResponseSuccess<?> addProvider(@RequestBody @Valid CreateProviderDto createProviderDto) {
        Provider provider = providerMapper.addProviderDto(createProviderDto);
       return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a provider successfully",
                providerService.save(provider)
        );
    }

    @GetMapping()
    public ResponseSuccess<?> getAllProviders() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Successfully get all providers ",
                providerService.findAll()
        );
    }
    @GetMapping("/{id}")
    public ResponseSuccess<?> getById(@PathVariable Long id) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get provider with id "+ id + " successfully",
                providerService.findById(id)
        );
    }
    @PutMapping("/{id}")
    public ResponseSuccess<?> update(@PathVariable Long id, @RequestBody @Valid CreateProviderDto createProviderDto) throws DataNotFoundException {
        Provider provider = providerMapper.updateProviderDto(id,createProviderDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Update provider with id "+ id + " successfully",
                providerService.save(provider)
        );
    }
    @PatchMapping("/{id}")
    public ResponseSuccess<?> updateProvider(@PathVariable Long id, @RequestBody @Valid Map<String,?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Update provider with id "+ id + " successfully",
                providerService.updatePatch(id,data)
        );
    }
}
