package com.sparta.springblog.service;


import com.sparta.springblog.dto.CommentRequestDto;
import com.sparta.springblog.dto.CommentResponseDto;
import com.sparta.springblog.entity.User;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto requestDto, User user);
    void deleteComment(Long id, User user);
    CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user);
}
