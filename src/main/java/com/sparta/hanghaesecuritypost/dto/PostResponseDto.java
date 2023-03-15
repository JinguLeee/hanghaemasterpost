package com.sparta.hanghaesecuritypost.dto;

import com.sparta.hanghaesecuritypost.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ReplyResponseDto> replyList = new ArrayList<>();
    private Long likeCount;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.likeCount = 0L;
    }

    public PostResponseDto(Post post, List<ReplyResponseDto> replyList, Long likeCount) {
        this(post);
        this.replyList = replyList;
        this.likeCount = likeCount;
    }
}
