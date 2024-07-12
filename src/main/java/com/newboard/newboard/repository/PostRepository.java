package com.newboard.newboard.repository;

import com.newboard.newboard.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Override
    Page<Post> findAll(Pageable pageable);

    @Override
    Optional<Post> findById(Long postid);
}
