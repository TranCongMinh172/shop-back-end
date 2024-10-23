package com.example.shop.controllers;

import com.example.shop.dtos.requests.SizeDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.SizeMapper;
import com.example.shop.models.Size;
import com.example.shop.dtos.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/sizes")
@RequiredArgsConstructor
public class SizeController {
    private final SizeService sizeService;
    private final SizeMapper sizeMapper;

    @PostMapping()
    public ResponseSuccess<?> createCategory(@RequestBody @Valid SizeDto sizeDto) {
        Size size = sizeMapper.addSizeDto2Size(sizeDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a size successfully",
                sizeService.save(size)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseSuccess<?> deleteCategory(@PathVariable Long id){
        sizeService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "delete size with id "+id+" successfully"
        );
    }
    @PutMapping("/{id}")
    public ResponseSuccess<?> updateCategory(@PathVariable Long id, @RequestBody @Valid SizeDto sizeDto){
       Size size = sizeMapper.addSizeDto2Size(sizeDto);
        size.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update size with id "+id+" successfully",
                sizeService.save(size)
        );
    }
    @GetMapping
    public ResponseSuccess<?> getAll(){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Successfully get all sizes",
                sizeService.findAll()
        );
    }
    @GetMapping("/{id}")
    public ResponseSuccess<?> getById(@PathVariable Long id){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get size with id "+ id +" successfully",
                sizeService.findById(id)
        );
    }
    @PatchMapping("/{id}")
    public ResponseSuccess<?> updatePatchCategory(@PathVariable Long id, @RequestBody @Valid Map<String,?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update size with id "+ id +" successfully",
                sizeService.updatePatch(id,data)
        );
    }
}
