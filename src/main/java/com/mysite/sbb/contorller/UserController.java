package com.mysite.sbb.contorller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.domain.UserEntity;
import com.mysite.sbb.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/signup")
    public String signup(UserEntity siteUser) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserEntity siteUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!siteUser.getPassword().equals(siteUser.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        // 중복 아이디 검증
        try {
            userService.create(siteUser.getUsername(), siteUser.getEmail(), siteUser.getPassword());
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 아이디입니다.");
            return "signup_form";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }
    
}
