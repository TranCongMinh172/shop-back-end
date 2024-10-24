package com.example.shop.service.interfaces;

import com.example.shop.dtos.responses.PageResponse;
import com.example.shop.exceptions.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService<T,ID>{
    T save(T t);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    T update(ID id, T t) throws DataNotFoundException;
    T updatePatch(ID id, Map<String, ?> data) throws DataNotFoundException;
    Page<T> findAll(Pageable pageable, Specification<T> specification);
    Page<T> findAll(Pageable pageable);
    PageResponse<?> getPageData(int pageNo, int pageSize, String[] search, String[] sort);
}
