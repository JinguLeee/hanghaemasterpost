package com.sparta.hanghaesecuritypost.service;

import com.sparta.hanghaesecuritypost.Exception.CustomErrorEnum;
import com.sparta.hanghaesecuritypost.Exception.CustomException;
import com.sparta.hanghaesecuritypost.dto.PostRequestDto;
import com.sparta.hanghaesecuritypost.dto.PostResponseDto;
import com.sparta.hanghaesecuritypost.dto.ReplyResponseDto;
import com.sparta.hanghaesecuritypost.entity.*;
import com.sparta.hanghaesecuritypost.repository.LikePostRepository;
import com.sparta.hanghaesecuritypost.repository.LikeReplyRepository;
import com.sparta.hanghaesecuritypost.repository.PostRepository;
import com.sparta.hanghaesecuritypost.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final LikePostRepository likePostRepository;
    private final LikeReplyRepository likeReplyRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.saveAndFlush(new Post(requestDto, user));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPost() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            List<ReplyResponseDto> replyResponseList = getReplyResponseList(post);
            postResponseDtoList.add(new PostResponseDto(post, replyResponseList, countPostLike(post.getId())));
        }

        return postResponseDtoList;
    }

    @Transactional
    public PostResponseDto getOnePost(Long postId) {
        Post post = getPost(postId);    // 게시글이 존재하는지 확인 후 가져온다
        return new PostResponseDto(post, getReplyResponseList(post), countPostLike(postId));
    }

    @Transactional
    public PostResponseDto update(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = getPost(postId);    // 게시글이 존재하는지 확인 후 가져온다

        checkPostRole(postId, user);  // 권한을 확인한다 (자신이 쓴 글인지 확인)

        post.update(postRequestDto);
        return new PostResponseDto(post, getReplyResponseList(post), countPostLike(postId));
    }

    private Long countPostLike(Long postId) {
        return likePostRepository.countByPostId(postId);
    }

    private List<ReplyResponseDto> getReplyResponseList (Post post) {
        List<ReplyResponseDto> replyResponseList = new ArrayList<>();
        for (Reply reply : post.getReplyList()) {
            replyResponseList.add(new ReplyResponseDto(reply, reply.getUser().getUsername(), countReplyLike(reply.getId())));
        }
        return replyResponseList;
    }

    private Long countReplyLike(Long replyId) {
        return likeReplyRepository.countByReplyId(replyId);
    }

    @Transactional
    public ResponseEntity<String> delete(Long postId, User user) {
        getPost(postId);        // 게시글이 존재하는지 확인 후 가져온다

        checkPostRole(postId, user);  // 권한을 확인한다 (자신이 쓴 글인지 확인)

        List<Reply> replyList = replyRepository.findAllByPostId(postId);
        for (Reply reply : replyList) {
            likeReplyRepository.deleteAllByReplyId(reply.getId());
        }
        replyRepository.deleteAllByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

    private void checkPostRole(Long id, User user) {
        if (user.getRole() == UserRoleEnum.ADMIN) return;
        postRepository.findByIdAndUser(id, user).orElseThrow(
                // TODO : 커스텀 한 예외처리 예시
                () -> new CustomException(CustomErrorEnum.NOROLE)
//                () -> new IllegalArgumentException("권한이 없습니다.")
        );
    }

    public ResponseEntity<String> likePost(Long postId, User user) {
        getPost(postId);        // 게시글이 존재하는지 확인 후 가져온다

        LikePost likePost = getLikePost(postId, user);  // 내가 좋아요 했는지 가져온다
        if (likePost == null){
            // 좋아요 없으면 좋아요 추가
            likePostRepository.saveAndFlush(new LikePost(postId, user));
            return ResponseEntity.status(HttpStatus.OK).body("좋아요");
        } else {
            // 좋아요 중이면 삭제
            likePostRepository.deleteById(likePost.getId());
            return ResponseEntity.status(HttpStatus.OK).body("좋아요 취소!");
        }
    }

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                // TODO : 커스텀 한 예외처리 예시
                () -> new CustomException(CustomErrorEnum.NOPOST)
//                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
    }

    private LikePost getLikePost(Long postId, User user) {
        return likePostRepository.findByPostIdAndUser(postId, user);
    }

}