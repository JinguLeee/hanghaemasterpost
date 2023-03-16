package com.sparta.hanghaesecuritypost.service;

import com.sparta.hanghaesecuritypost.dto.ReplyRequestDto;
import com.sparta.hanghaesecuritypost.dto.ReplyResponseDto;
import com.sparta.hanghaesecuritypost.entity.*;
import com.sparta.hanghaesecuritypost.repository.LikeRepository;
import com.sparta.hanghaesecuritypost.repository.PostRepository;
import com.sparta.hanghaesecuritypost.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public ReplyResponseDto createReply(Long postId, ReplyRequestDto requestDto, User user) {
        Post post = getPost(postId);  // 게시글이 존재하는지 확인 후 가져온다
        Reply reply = replyRepository.saveAndFlush(new Reply(requestDto.getReply(), user, post));
        return new ReplyResponseDto(reply, user.getUsername(), 0L);
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
    }

    @Transactional
    public ReplyResponseDto update(Long replyId, ReplyRequestDto replyRequestDto, User user) {
        Reply reply = getReply(replyId); // 댓글이 존재하는지 확인 후 가져온다.
        checkReplyRole(replyId, user);  // 권한을 확인한다 (자신이 쓴 댓글인지 확인)
        reply.update(replyRequestDto);
        return new ReplyResponseDto(reply, user.getUsername(), countLike(LikeEnum.REPLY, replyId));
    }

    private Long countLike(LikeEnum likeEnum, Long likeId) {
        return likeRepository.countByIndexAndLikeId(likeEnum.getIndex(), likeId);
    }

    @Transactional
    public ResponseEntity<String> delete(Long replyId, User user) {
        getReply(replyId); // 댓글이 존재하는지 확인 후 가져온다.
        checkReplyRole(replyId, user);  // 권한을 확인한다 (자신이 쓴 댓글인지 확인)
        likeRepository.deleteAllByIndexAndLikeId(LikeEnum.REPLY.getIndex(), replyId);
        replyRepository.deleteById(replyId);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 완료");
    }

    private void checkReplyRole(Long replyId, User user) {
        if (user.getRole() == UserRoleEnum.ADMIN) return;
        replyRepository.findByIdAndUser(replyId, user).orElseThrow(
                () -> new IllegalArgumentException("권한이 없습니다.")
        );
    }

    @Transactional
    public ResponseEntity<String> likeReply(Long replyId, User user) {
        Reply reply = getReply(replyId);

        Optional<Like> like = getlike(LikeEnum.REPLY, replyId, user);
        if (like.isEmpty()) {   // 좋아요 없으면 좋아요 추가
            saveLike(LikeEnum.REPLY, reply.getPost().getId(), replyId, user);
            return ResponseEntity.status(HttpStatus.OK).body("좋아요");
        } else {                // 좋아요 중이면 삭제
            deleteLike(like.get().getId());
            return ResponseEntity.status(HttpStatus.OK).body("좋아요 취소!");
        }
    }

    private Reply getReply(Long replyId){
        return replyRepository.findById(replyId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
    }

    private Optional<Like> getlike(LikeEnum likeEnum, Long likeId, User user) {
        return likeRepository.findByIndexAndLikeIdAndUser(likeEnum.getIndex(), likeId, user);
    }

    private void saveLike(LikeEnum post, Long postId, Long likeId, User user) {
        likeRepository.saveAndFlush(new Like(post, postId, likeId, user));
    }

    private void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }

}