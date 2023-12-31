package com.sparta.springblog.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

import com.sparta.springblog.dto.PostListResponseDto;
import com.sparta.springblog.dto.PostRequestDto;
import com.sparta.springblog.dto.PostResponseDto;
import com.sparta.springblog.entity.Post;
import com.sparta.springblog.entity.PostLike;
import com.sparta.springblog.entity.User;
import com.sparta.springblog.entity.UserRoleEnum;
import com.sparta.springblog.exception.NotHaveRoleException;
import com.sparta.springblog.exception.PostNotFoundException;
import com.sparta.springblog.repository.PostLikeRepository;
import com.sparta.springblog.repository.PostRepository;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final MessageSource messageSource;

    @Override
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);
        //2.oject.isnull(->null check)
        //3.post에게 method 만들어서 역할을 위임
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
            throw new NotHaveRoleException(
                             messageSource.getMessage(
                            "not.have.role",
                            null,
                            "Not Have Role",
                            Locale.getDefault()
                    )
            );
        }

        postRepository.delete(post);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        Post post = findPost(id);

        // 게시글 작성자(post.user) 와 요청자(user) 가 같은지 또는 Admin 인지 체크 (아니면 예외발생)
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || post.getUser().equals(user))) {
            throw new NotHaveRoleException(
                    messageSource.getMessage(
                    "not.have.role",
                    null,
                    "Not Have Role",
                    Locale.getDefault())
            );
        }

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    @Override
    public Post findPost(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException(
                        messageSource.getMessage(
                                "not.found.post",
                                null,
                                "Wrong Post",
                                Locale.getDefault()
                        )
                )
        );
    }

    @Override
    @Transactional
    public void likePost(Long id, User user) {
        Post post = findPost(id);

        // 아래 조건 코드와 동일
        // if (postLikeRepository.findByUserAndPost(user, post).isPresent()) {
        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new DuplicateRequestException("이미 좋아요 한 게시글 입니다.");
        } else {
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
        }
    }

    @Override
    @Transactional
    public void deleteLikePost(Long id, User user) {
        Post post = findPost(id);
        Optional<PostLike> postLikeOptional = postLikeRepository.findByUserAndPost(user, post);
        if (postLikeOptional.isPresent()) {
            postLikeRepository.delete(postLikeOptional.get());
        } else {
            throw new IllegalArgumentException("해당 게시글에 취소할 좋아요가 없습니다.");
        }
    }
}
