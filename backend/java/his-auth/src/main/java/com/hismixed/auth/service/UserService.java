package com.hismixed.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hismixed.auth.entity.User;
import com.hismixed.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .isNull(User::getDeletedAt)
        );
    }

    public User findById(Long id) {
        return userRepository.selectById(id);
    }

    public void createUser(User user) {
        userRepository.insert(user);
    }
}