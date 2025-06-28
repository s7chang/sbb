package com.mysite.sbb.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.domain.SiteUser;
import com.mysite.sbb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = SiteUser.builder().username(username).email(email).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);

        return user;
    }
}
