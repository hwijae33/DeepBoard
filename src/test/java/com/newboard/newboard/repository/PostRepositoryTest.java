package com.newboard.newboard.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

//    @Test
//    public void insertDummies(){
//        IntStream.rangeClosed(1,300).forEach(i ->{
//
//            Post post = Post.builder()
//                    .title("Title...."+ i)
//                    .content("Content..."+i)
//                    .userid(i)
//                    .build();
//            System.out.println(postRepository.save(post));
//        });
//    }
}
