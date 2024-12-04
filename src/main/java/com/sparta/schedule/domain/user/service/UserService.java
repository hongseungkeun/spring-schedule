package com.sparta.schedule.domain.user.service;

import com.sparta.schedule.domain.user.dto.UserLoginReq;
import com.sparta.schedule.domain.user.dto.UserSignUpReq;
import com.sparta.schedule.domain.user.entity.User;
import com.sparta.schedule.domain.user.exception.AlreadyExistUserException;
import com.sparta.schedule.domain.user.exception.UserNotFoundException;
import com.sparta.schedule.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signUp(UserSignUpReq request) {
        isExistEmail(request.email());

        userRepository.save(request);
    }

    public Long login(UserLoginReq request) {
        User user = findUserByEmail(request.email());

        user.isPossibleLogin(request.password());

        return user.getUserId();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다"));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다"));
    }

    private void isExistEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistUserException("이미 존재하는 이메일입니다.");
        }
    }
}