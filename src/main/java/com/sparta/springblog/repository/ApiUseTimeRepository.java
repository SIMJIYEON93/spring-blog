package com.sparta.springblog.repository;


import com.sparta.springblog.entity.ApiUseTime;
import com.sparta.springblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User user);
}