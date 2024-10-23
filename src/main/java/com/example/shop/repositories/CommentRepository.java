package com.example.shop.repositories;

import com.example.shop.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends BaseRepository<Comment, Long> {
    List<Comment> findAllByProductId(Long id);
}