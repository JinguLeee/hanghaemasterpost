package com.sparta.hanghaesecuritypost.controller;

import com.sparta.hanghaesecuritypost.dto.ReplyRequestDto;
import com.sparta.hanghaesecuritypost.dto.ReplyResponseDto;
import com.sparta.hanghaesecuritypost.security.UserDetailsImpl;
import com.sparta.hanghaesecuritypost.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "댓글 등록", notes = "게시글의 댓글을 등록한다.")
    @PostMapping("/{postId}")
    public ReplyResponseDto createReply(@PathVariable Long postId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.createReply(postId, replyRequestDto, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 수정", notes = "자신이 쓴 댓글 중 선택한 댓글을 수정한다.")
    @PutMapping("/{replyId}")
    public ReplyResponseDto updateReply(@PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.update(replyId, replyRequestDto, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 삭제", notes = "자신이 쓴 댓글 중 선택한 댓글을 삭제한다.")
    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.delete(replyId, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 좋아요", notes = "댓글 좋아요를 등록, 취소한다.")
    @PostMapping("like/{replyId}")
    public ResponseEntity<String> likeReply(@PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.likeReply(replyId, userDetails.getUser());
    }
}
