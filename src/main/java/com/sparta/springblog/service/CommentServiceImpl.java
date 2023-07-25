package com.sparta.springblog.service;

import java.util.concurrent.RejectedExecutionException;

import com.sparta.springblog.dto.CommentRequestDto;
import com.sparta.springblog.dto.CommentResponseDto;
import com.sparta.springblog.entity.Comment;
import com.sparta.springblog.entity.Post;
import com.sparta.springblog.entity.User;
import com.sparta.springblog.entity.UserRoleEnum;
import com.sparta.springblog.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final PostServiceImpl postServiceImpl;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        Post post = postServiceImpl.findPost(requestDto.getPostId());
        Comment comment = new Comment(requestDto.getBody());
        comment.setUser(user);
        comment.setPost(post);

        var savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    @Override
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException("작성자만 삭제 할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException("작성자만 수정 할 수 있습니다.");
        }

        comment.setBody(requestDto.getBody());

        return new CommentResponseDto(comment);
    }

}
