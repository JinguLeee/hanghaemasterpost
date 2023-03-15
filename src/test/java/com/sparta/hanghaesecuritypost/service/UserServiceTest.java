package com.sparta.hanghaesecuritypost.service;

import com.sparta.hanghaesecuritypost.dto.PostRequestDto;
import com.sparta.hanghaesecuritypost.entity.Post;
import com.sparta.hanghaesecuritypost.entity.User;
import com.sparta.hanghaesecuritypost.entity.UserRoleEnum;
import com.sparta.hanghaesecuritypost.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Mock
    User user;

    @Test
    @DisplayName("회원 탈퇴")
    void deleteId_Success() {
        user = new User("jingu", "12345678910", UserRoleEnum.USER);

        PostRequestDto postRequestDto = new PostRequestDto("제목", "내용");
        Post post = new Post(postRequestDto, user);


    }
}