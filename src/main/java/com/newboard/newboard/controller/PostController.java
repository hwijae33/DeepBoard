package com.newboard.newboard.controller;

import com.newboard.newboard.dto.PostOneDTO;
import com.newboard.newboard.service.PostServiceImpl;
import com.newboard.newboard.service.UserPrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;


    @GetMapping("/post/postDetail/{id}")
    public String postDetail(@PathVariable("id")Long id,@AuthenticationPrincipal UserPrincipalDetails users,Model model){

        if(users != null) {
            model.addAttribute("user",users.getUsername());
        }

        // 게시물 조회
        Optional<PostOneDTO> post = postService.findById(id);

        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "/post/postDetail";
        } else {

            return "redirect:/error/post403";
        }
    }

    @GetMapping("/write")
    public String write(){
        return "post/write";
    }

    @GetMapping("/update")
    public String update(){
        return "post/update";
    }

    @GetMapping("/post/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @GetMapping("/error/post403")
    public String handlePost403Error() {
        // 처리 로직
        return "error/403";
    }
}
