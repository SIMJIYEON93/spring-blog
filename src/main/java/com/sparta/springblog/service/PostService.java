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

    /**
     * 게시글 좋아요
     * @param id 좋아요 요청 게시글 ID
     * @param user 게시글 좋아요 요청자
     */
    void likePost(Long id, User user);

    /**
     * 게시글 좋아요 취소
     * @param id 좋아요 취소 요청 게시글 ID
     * @param user 게시글 좋아요 취소 요청자
     */
    void deleteLikePost(Long id, User user);
}
