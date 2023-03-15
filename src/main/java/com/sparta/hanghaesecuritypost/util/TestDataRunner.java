package com.sparta.hanghaesecuritypost.util;

import com.sparta.hanghaesecuritypost.controller.PostController;
import com.sparta.hanghaesecuritypost.entity.Post;
import com.sparta.hanghaesecuritypost.entity.User;
import com.sparta.hanghaesecuritypost.entity.UserRoleEnum;
import com.sparta.hanghaesecuritypost.repository.PostRepository;
import com.sparta.hanghaesecuritypost.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {
    private final PostController postController;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        User testUser1 = new User("jingu", "123", UserRoleEnum.USER);
//        User testUser2 = new User("jingu2", "123", UserRoleEnum.USER);
//        testUser1 = userRepository.save(testUser1);
//        testUser2 = userRepository.save(testUser2);
    }

}
