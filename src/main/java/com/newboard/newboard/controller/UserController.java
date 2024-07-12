package com.newboard.newboard.controller;

import com.newboard.newboard.domain.User;
import com.newboard.newboard.dto.SignUpFormDTO;

import com.newboard.newboard.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl USERSERVICE;

    @GetMapping("/user/loginForm")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("/user/register")
    public String register() {
        return "/user/register";
    }

    @PostMapping("/register")
    public String auth(@ModelAttribute("sign") SignUpFormDTO sign, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 이메일 중복 체크
        User user = USERSERVICE.existsByEmail(sign.getEmail());

        if (user == null) {
            // 중복되지 않은 경우 회원 가입 처리
            USERSERVICE.save(sign);
            return "redirect:/user/loginForm";
        } else {
            bindingResult.rejectValue("email", "Duplicate.email", "중복된 이메일입니다.");
            redirectAttributes.addFlashAttribute("error", "중복된 이메일입니다.");
            return "redirect:/error/user403";
        }
    }

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @GetMapping("/error/user403")
    public String handleUser403Error() {
        // 처리 로직
        return "error/403";
    }

}