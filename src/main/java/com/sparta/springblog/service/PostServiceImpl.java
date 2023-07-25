package com.sparta.springblog.service;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

import com.sparta.springblog.dto.PostListResponseDto;
import com.sparta.springblog.dto.PostRequestDto;
import com.sparta.springblog.dto.PostResponseDto;
import com.sparta.springblog.entity.Post;
import com.sparta.springblog.entity.User;
import com.sparta.springblog.entity.UserRoleEnum;
import com.sparta.springblog.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    @Override
    public PostListResponseDto getPosts() {
        List<PostResponseDto> postList = postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return new PostListResponseDto(postList);
    }

    @Override
    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);

        return new PostResponseDto(post);
    }

    @Override
    public void deletePost(Long id, User user) {
        Post post = findPost(id);

        // 게시글 작성자(post.user) 와 요청자(user) 가 같은지 또는 Admin 인지 체크 (아니면 예외발생)
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || post.getUser().equals(user))) {
            throw new RejectedExecutionException("작성자만 삭제 할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        Post post = findPost(id);

        // 게시글 작성자(post.user) 와 요청자(user) 가 같은지 또는 Admin 인지 체크 (아니면 예외발생)
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || post.getUser().equals(user))) {
            throw new RejectedExecutionException("작성자만 수정 할 수 있습니다.");
        }

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    @Override
    public Post findPost(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
