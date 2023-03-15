package com.sparta.hanghaesecuritypost.controller;

import com.sparta.hanghaesecuritypost.dto.PostRequestDto;
import com.sparta.hanghaesecuritypost.dto.PostResponseDto;
import com.sparta.hanghaesecuritypost.security.UserDetailsImpl;
import com.sparta.hanghaesecuritypost.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "게시글 등록", notes = "게시글을 등록한다.")
    @PostMapping()
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto, userDetails.getUser());
    }

    @ApiOperation(value = "게시글 조회", notes = "전체 게시글을 조회한다.")
    @GetMapping()
    public List<PostResponseDto> getPosts() {
        return postService.getPost();
    }

    @ApiOperation(value = "게시글 조회", notes = "하나의 게시글을 조회한다.")
    @GetMapping("/{postId}")
    public PostResponseDto getDetail(@PathVariable Long postId) {
        return postService.getOnePost(postId);
    }

    @ApiOperation(value = "게시글 수정", notes = "자신이 쓴 게시글 중 선택한 게시글을 수정한다.")
    @PutMapping("/{postId}")
    public PostResponseDto updatePost (@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.update(postId, postRequestDto, userDetails.getUser());
    }

    @ApiOperation(value = "게시글 삭제", notes = "자신이 쓴 게시글 중 선택한 게시글을 삭제한다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.delete(postId, userDetails.getUser());
    }

    @ApiOperation(value = "게시글 좋아요", notes = "게시글 좋아요를 등록, 취소한다.")
    @PostMapping("like/{postId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.likePost(postId, userDetails.getUser());
    }
}
