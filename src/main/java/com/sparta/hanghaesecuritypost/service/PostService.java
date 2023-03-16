package com.sparta.hanghaesecuritypost.service;

import com.sparta.hanghaesecuritypost.Exception.CustomErrorEnum;
import com.sparta.hanghaesecuritypost.Exception.CustomException;
import com.sparta.hanghaesecuritypost.dto.PostRequestDto;
import com.sparta.hanghaesecuritypost.dto.PostResponseDto;
import com.sparta.hanghaesecuritypost.dto.ReplyResponseDto;
import com.sparta.hanghaesecuritypost.entity.*;
import com.sparta.hanghaesecuritypost.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.saveAndFlush(new Post(requestDto, user));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            List<ReplyResponseDto> replyResponseList = getReplyResponseList(post);
            postResponseDtoList.add(new PostResponseDto(post, replyResponseList, countLike(LikeEnum.POST, post.getId())));
        }

        return postResponseDtoList;
    }

    @Transactional
    public PostResponseDto getOnePost(Long postId) {
        Post post = getPostList(postId);    // 게시글이 존재하는지 확인 후 가져온다
        return new PostResponseDto(post, getReplyResponseList(post), countLike(LikeEnum.POST, postId));
    }

    @Transactional
    public PostResponseDto update(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = getPostList(postId);    // 게시글이 존재하는지 확인 후 가져온다

        checkPostRole(postId, user);  // 권한을 확인한다 (자신이 쓴 글인지 확인)

        post.update(postRequestDto);
        return new PostResponseDto(post, getReplyResponseList(post), countLike(LikeEnum.POST, postId));
    }

    private List<ReplyResponseDto> getReplyResponseList (Post post) {
        List<ReplyResponseDto> replyResponseList = new ArrayList<>();
        for (Reply reply : post.getReplyList()) {
            replyResponseList.add(new ReplyResponseDto(reply, reply.getUser().getUsername(), countLike(LikeEnum.REPLY, reply.getId())));
        }
        return replyResponseList;
    }

    private Long countLike(LikeEnum likeEnum, Long likeId) {
        return likeRepository.countByIndexAndLikeId(likeEnum.getIndex(), likeId);
    }

    @Transactional
    public ResponseEntity<String> delete(Long postId, User user) {
        getPostList(postId);        // 게시글이 존재하는지 확인 후 가져온다

        checkPostRole(postId, user);  // 권한을 확인한다 (자신이 쓴 글인지 확인)

        List<Reply> replyList = replyRepository.findAllByPostId(postId);
        for (Reply reply : replyList) {
            likeRepository.deleteAllByIndexAndLikeId(LikeEnum.REPLY.getIndex(), reply.getId());
        }
        replyRepository.deleteAllByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

    private void checkPostRole(Long postId, User user) {
        if (user.getRole() == UserRoleEnum.ADMIN) return;
        postRepository.findByIdAndUser(postId, user).orElseThrow(
                // () -> new IllegalArgumentException("권한이 없습니다.")

                // TODO : 커스텀 한 예외처리 예시
                () -> new CustomException(CustomErrorEnum.NOROLE)
        );
    }

    @Transactional
    public ResponseEntity<String> likePost(Long postId, User user) {
        getPostList(postId);

        Optional<Like> like = getlike(LikeEnum.POST, postId, user);
        if (like.isEmpty()) {
            saveLike(LikeEnum.POST, postId, user);
            return ResponseEntity.status(HttpStatus.OK).body("좋아요");
        } else {                // 좋아요 중이면 삭제
            deleteLike(like.get().getId());
            return ResponseEntity.status(HttpStatus.OK).body("좋아요 취소!");
        }
    }

    private Post getPostList(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                // () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")

                // TODO : 커스텀 한 예외처리 예시
                () -> new CustomException(CustomErrorEnum.NOPOST)
        );
    }

    private Optional<Like> getlike(LikeEnum likeEnum, Long likeId, User user) {
        return likeRepository.findByIndexAndLikeIdAndUser(likeEnum.getIndex(), likeId, user);
    }

    private void saveLike(LikeEnum post, Long likeId, User user) {
        likeRepository.saveAndFlush(new Like(post, likeId, user));
    }

    private void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }

}