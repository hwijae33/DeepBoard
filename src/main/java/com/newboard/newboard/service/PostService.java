package com.newboard.newboard.service;

import com.newboard.newboard.dto.ListDTO;
import com.newboard.newboard.dto.PostOneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface PostService {
    Page<ListDTO> getAllPosts(Pageable pageable);

    Optional<PostOneDTO> findById(Long postid);
}
