package com.sparta.hanghaesecuritypost.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public LikePost(Long postId, User user) {
        this.postId = postId;
        this.user = user;
    }
}
