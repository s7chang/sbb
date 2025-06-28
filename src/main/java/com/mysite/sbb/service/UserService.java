package com.mysite.sbb.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.domain.UserEntity;
import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity create(String username, String email, String password) {
        UserEntity user = UserEntity.builder().username(username).email(email).password(passwordEncoder.encode(password)).password2("NotSave").build();
        userRepository.save(user);

        return user;
    }

    // 답변 정보 획득
    public UserEntity getUser(String username) {
        Optional<UserEntity> siteUser = userRepository.findByUsername(username);

        if (siteUser.isEmpty()) {
            throw new DataNotFoundException("siteuser not found");
        }

        return siteUser.get();

    }
}
