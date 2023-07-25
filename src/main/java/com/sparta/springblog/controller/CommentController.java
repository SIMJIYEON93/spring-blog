package com.sparta.springblog.controller;

import com.sparta.springblog.dto.ApiResponseDto;
import com.sparta.springblog.dto.CommentRequestDto;
import com.sparta.springblog.dto.CommentResponseDto;
import com.sparta.springblog.entity.ApiUseTime;
import com.sparta.springblog.entity.User;
import com.sparta.springblog.repository.ApiUseTimeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.springblog.security.UserDetailsImpl;
import com.sparta.springblog.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentServiceImpl;
    private final ApiUseTimeRepository apiUseTimeRepository;


    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        long startTime = System.currentTimeMillis();

        try {  CommentResponseDto result = commentServiceImpl.createComment(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }  finally {
        long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;

        User loginUser = userDetails.getUser();

        // API 사용시간 및 DB 에 기록
        ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser)
                .orElse(null);
        if (apiUseTime == null) {
            apiUseTime = new ApiUseTime(loginUser, runTime);
        } else {
            apiUseTime.addUseTime(runTime);
        }

        System.out.println("[API Use Time] Username: " + loginUser.getUsername() + ", Total Time: " + apiUseTime.getTotalTime() + " ms");
        apiUseTimeRepository.save(apiUseTime);
    }}



    @PutMapping("/comments/{id}")
    public ResponseEntity<ApiResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
            CommentResponseDto result = commentServiceImpl.updateComment(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
        }
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
            commentServiceImpl.deleteComment(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
        }
    }

