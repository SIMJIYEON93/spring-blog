package com.sparta.springblog.service;

import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;

import com.sparta.springblog.dto.CommentRequestDto;
import com.sparta.springblog.dto.CommentResponseDto;
import com.sparta.springblog.entity.Comment;
import com.sparta.springblog.entity.Post;
import com.sparta.springblog.entity.User;
import com.sparta.springblog.entity.UserRoleEnum;
import com.sparta.springblog.exception.NotHaveRoleException;
import com.sparta.springblog.repository.CommentRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final PostServiceImpl postServiceImpl;
    private final CommentRepository commentRepository;
    private final MessageSource messageSource;

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
            throw new NotHaveRoleException(
                    messageSource.getMessage(
                    "not.have.role",
                    null,
                    "Not Have Role",
                    Locale.getDefault())
            );
        }

        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new NotHaveRoleException(
                             messageSource.getMessage(
                            "not.have.role",
                            null,
                            "Not Have Role",
                            Locale.getDefault()
                             )
            );
        }

        comment.setBody(requestDto.getBody());

        return new CommentResponseDto(comment);
    }

}
