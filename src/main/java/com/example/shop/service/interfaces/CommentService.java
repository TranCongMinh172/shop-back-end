package com.example.shop.service.interfaces;

import com.example.shop.dtos.requests.CommentDto;
import com.example.shop.dtos.responses.CommentResponse;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.MediaTypeNotSupportException;
import com.example.shop.models.Comment;

import java.io.IOException;

public interface CommentService extends BaseService<Comment,Long>{
    CommentResponse addComment(CommentDto commentDto) throws DataNotFoundException, IOException, MediaTypeNotSupportException;

}
