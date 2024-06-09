package com.example.shop.service.impls;

import com.example.shop.models.Comment;
import com.example.shop.service.interfaces.CommentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Long> implements CommentService {
    public CommentServiceImpl(JpaRepository<Comment, Long> repository) {
        super(repository);
    }
}
