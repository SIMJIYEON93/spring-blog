package com.sparta.springblog.repository;

import java.util.Optional;

import com.sparta.springblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
