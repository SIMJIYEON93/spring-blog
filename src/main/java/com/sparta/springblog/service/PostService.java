package com.sparta.springblog.service;


import com.sparta.springblog.dto.PostListResponseDto;
import com.sparta.springblog.dto.PostRequestDto;
import com.sparta.springblog.dto.PostResponseDto;
import com.sparta.springblog.entity.Post;
import com.sparta.springblog.entity.User;

public interface PostService {
    PostResponseDto createPost(PostRequestDto requestDto, User user);
    PostListResponseDto getPosts();
    PostResponseDto getPostById(Long id);
    void deletePost(Long id, User user);
    PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user);
    Post findPost(long id);
}
