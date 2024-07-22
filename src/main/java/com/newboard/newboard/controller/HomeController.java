package com.newboard.newboard.controller;


import com.newboard.newboard.dto.ListDTO;
import com.newboard.newboard.service.PostServiceImpl;
import com.newboard.newboard.service.UserPrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final PostServiceImpl postService;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(page = 0, size = 15, direction = Sort.Direction.DESC) Pageable pageable,@AuthenticationPrincipal UserPrincipalDetails users) {
        // Retrieve page of posts
        Page<ListDTO> posts = postService.getAllPosts(pageable);

        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", posts.getNumber());
        model.addAttribute("totalPages", posts.getTotalPages());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication : " + authentication);
        System.out.println("principal : " + authentication.getPrincipal());

        if(users != null) {
            model.addAttribute("user",users.getUsername());
        }


        return "index";
    }


}
