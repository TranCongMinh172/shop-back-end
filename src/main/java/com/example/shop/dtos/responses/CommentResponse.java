package com.example.shop.dtos.responses;


import com.example.shop.models.Comment;
import com.example.shop.models.CommentMedia;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Comment comment;
    private List<CommentMedia> commentMedia;
}
