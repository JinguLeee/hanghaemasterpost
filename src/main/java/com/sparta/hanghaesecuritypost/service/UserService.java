package com.sparta.hanghaesecuritypost.service;

import com.sparta.hanghaesecuritypost.dto.LoginRequestDto;
import com.sparta.hanghaesecuritypost.dto.SignupRequestDto;
import com.sparta.hanghaesecuritypost.entity.LikeEnum;
import com.sparta.hanghaesecuritypost.entity.Post;
import com.sparta.hanghaesecuritypost.entity.Reply;
import com.sparta.hanghaesecuritypost.entity.User;
import com.sparta.hanghaesecuritypost.jwt.JwtUtil;
import com.sparta.hanghaesecuritypost.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity<String> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();

        // 회원 중복 확인
        userRepository.findByUsername(username).ifPresent(it -> {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        });

        userRepository.save(new User(username, signupRequestDto.getPassword(), signupRequestDto.getRole()));
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
    }

    public ResponseEntity<String> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 사용자 확인
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(loginRequestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }

    @Transactional
    public ResponseEntity<String> deleteId(User user, HttpServletResponse response) {
        // 사용자 확인
        userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 좋아요 삭제
        likeRepository.deleteAllByUser(user);

        // 댓글 삭제
        List<Reply> replyList = replyRepository.findAllByUser(user);
        for (Reply reply : replyList) {
            likeRepository.deleteAllByIndexAndLikeId(LikeEnum.REPLY.getIndex(), reply.getId());
        }
        replyRepository.deleteAllByUser(user);

        // 게시글 삭제
        List<Post> postList = postRepository.findAllByUser(user);
        for (Post post : postList) {
            likeRepository.deleteAllByIndexAndLikeId(LikeEnum.POST.getIndex(), post.getId());
            replyList = replyRepository.findAllByPostId(post.getId());
            for (Reply reply : replyList) {
                likeRepository.deleteAllByIndexAndLikeId(LikeEnum.REPLY.getIndex(), reply.getId());
            }
            replyRepository.deleteAllByPostId(post.getId());

            postRepository.deleteById(post.getId());
        }

        // 아이디 삭제
        userRepository.delete(user);

        // 로그아웃
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, null);
        return ResponseEntity.status(HttpStatus.OK).body("회원 탈퇴 완료");
    }
}