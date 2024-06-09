package com.example.shop.repositories;

import com.example.shop.models.CommentMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentMediaRepository extends JpaRepository<CommentMedia, Long> {
}