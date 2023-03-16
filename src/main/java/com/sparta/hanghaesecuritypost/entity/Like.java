package com.sparta.hanghaesecuritypost.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "likelist")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int index;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Like(LikeEnum likeEnum, Long postId, Long likeId, User user) {
        this.index = likeEnum.getIndex();
        this.postId = postId;
        this.likeId = likeId;
        this.user = user;
    }
}
