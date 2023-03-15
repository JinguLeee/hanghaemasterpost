package com.sparta.hanghaesecuritypost.dto;

import com.sparta.hanghaesecuritypost.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReplyResponseDto {
    private Long id;
    private String reply;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;

    private Long likeCount;

    public ReplyResponseDto(Reply reply, String username, Long likeCount) {
        this.id = reply.getId();
        this.reply = reply.getReply();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
        this.username = username;
        this.likeCount = likeCount;
    }

}