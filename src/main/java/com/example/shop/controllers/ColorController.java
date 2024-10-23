package com.example.shop.controllers;

import com.example.shop.dtos.requests.ColorDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.ColorMapper;
import com.example.shop.dtos.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.ColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;
    private final ColorMapper colorMapper;
    @PostMapping()
    public ResponseSuccess<?> createCategory(@RequestBody @Valid ColorDto colorDto) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a color successfully",
                colorService.save(colorMapper.addColorDto2ColorDto(colorDto))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseSuccess<?> deleteCategory(@PathVariable Long id){
        colorService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "delete color with id "+id+" successfully"
        );
    }
    @PutMapping("/{id}")
    public ResponseSuccess<?> updateCategory(@PathVariable Long id, @RequestBody @Valid ColorDto colorDto){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update color with id "+id+" successfully",
                colorService.save(colorMapper.addColorDto2ColorDto(colorDto))
        );
    }
    @GetMapping
    public ResponseSuccess<?> getAll(){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Successfully get all colors",
                colorService.findAll()
        );
    }
    @GetMapping("/{id}")
    public ResponseSuccess<?> getById(@PathVariable Long id){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get color with id "+ id +" successfully",
                colorService.findById(id)
        );
    }
    @PatchMapping("/{id}")
    public ResponseSuccess<?> updatePatchCategory(@PathVariable Long id, @RequestBody @Valid Map<String,?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update color with id "+ id +" successfully",
                colorService.updatePatch(id,data)
        );
    }
}
