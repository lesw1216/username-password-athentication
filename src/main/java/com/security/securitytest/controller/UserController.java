package com.security.securitytest.controller;

import com.security.securitytest.dto.UserDTO;
import com.security.securitytest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String loginForm(@RequestBody String username, String password) {
        log.info("username = {}, password = {}", username, password);
        return "/index";
    }

    @GetMapping("/join")
    public String join() {
        return "/user/join";
    }

    @PostMapping("/join")
    public String joinForm(String username, String password) {
        log.info("username = {}, password = {}", username, password);
        UserDTO userDTO = new UserDTO(username, password);
        if (userService.save(userDTO)) {
            // 가입 성공으로 로그인하러 로그인 페이지로 이동.
            return "/user/login";
        } else {
            return "redirect:/join";
        }

    }
}
