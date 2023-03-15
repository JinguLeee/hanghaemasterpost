package com.sparta.hanghaesecuritypost.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class LikeReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public LikeReply(Long replyId, User user) {
        this.replyId = replyId;
        this.user = user;
    }
}
