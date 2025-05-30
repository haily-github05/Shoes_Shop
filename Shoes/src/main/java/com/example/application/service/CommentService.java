package com.example.application.service;

import com.example.application.model.request.CreateCommentPostRequest;
import com.example.application.model.request.CreateCommentProductRequest;
import com.example.application.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Comment createCommentPost(CreateCommentPostRequest createCommentPostRequest, long userId);
    Comment createCommentProduct(CreateCommentProductRequest createCommentProductRequest, long userId);
}
