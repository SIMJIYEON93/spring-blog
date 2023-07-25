package com.sparta.springblog.exception;


import com.sparta.springblog.dto.ApiResponseDto;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.RejectedExecutionException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class,RejectedExecutionException.class, DuplicateRequestException.class})
    public ResponseEntity<ApiResponseDto> ExceptionHandler(IllegalArgumentException ex) {
        ApiResponseDto restApiException = new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
        @ExceptionHandler({NullPointerException.class})
        public ResponseEntity<ApiResponseDto> nullPointerExceptionHandler(NullPointerException ex) {
            ApiResponseDto restApiException = new ApiResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(
                    // HTTP body
                    restApiException,
                    // HTTP status code
                    HttpStatus.NOT_FOUND
            );
        }

        @ExceptionHandler({NotHaveRoleException.class})
        public ResponseEntity<ApiResponseDto> notHaveRoleExceptionHandler(NotHaveRoleException ex) {
            ApiResponseDto restApiException = new ApiResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(
                    // HTTP body
                    restApiException,
                    // HTTP status code
                    HttpStatus.NOT_FOUND
            );
        }

    @ExceptionHandler({PostNotFoundException.class})
    public ResponseEntity<ApiResponseDto> postNotFoundExceptionHandler(PostNotFoundException ex) {
        ApiResponseDto restApiException = new ApiResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }
    }



