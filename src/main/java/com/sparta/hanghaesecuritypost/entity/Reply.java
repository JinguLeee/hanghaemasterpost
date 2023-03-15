package com.sparta.hanghaesecuritypost.entity;

import com.sparta.hanghaesecuritypost.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reply;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    public Reply(String reply, User user, Post post) {
        this.reply = reply;
        this.user = user;
        this.post = post;
    }

    @Transactional
    public void update(ReplyRequestDto replyRequestDto) {
        this.reply = replyRequestDto.getReply();
    }
}
