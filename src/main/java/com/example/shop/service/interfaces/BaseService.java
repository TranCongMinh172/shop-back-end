package com.example.shop.service.interfaces;

import com.example.shop.exceptions.DataNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService<T,ID>{
    T save(T t);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    T update(ID id, T t);
    T updatePatch(ID id, Map<String, ?> data) throws DataNotFoundException;
}
