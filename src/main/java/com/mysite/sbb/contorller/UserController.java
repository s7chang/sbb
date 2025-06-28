package com.mysite.sbb.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.domain.SiteUser;
import com.mysite.sbb.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signup(SiteUser userToCreate) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid SiteUser userToCreate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userToCreate.getPassword().equals(userToCreate.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        userService.create(userToCreate.getUsername(), userToCreate.getEmail(), userToCreate.getPassword());

        return "redirect:/";
    }
    
}
