package com.sparta.springblog.service;


import com.sparta.springblog.dto.AuthRequestDto;

public interface UserService {

    void signup(AuthRequestDto requestDto);
    void login(AuthRequestDto requestDto);
}
