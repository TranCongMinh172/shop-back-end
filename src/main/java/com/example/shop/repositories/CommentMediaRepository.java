package com.example.shop.repositories;

import com.example.shop.models.CommentMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentMediaRepository extends BaseRepository<CommentMedia, Long> {
    List<CommentMedia> findAllByCommentId(Long id);
}