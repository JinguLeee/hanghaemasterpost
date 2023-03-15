package com.sparta.hanghaesecuritypost.repository;


import com.sparta.hanghaesecuritypost.entity.LikePost;
import com.sparta.hanghaesecuritypost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    LikePost findByPostIdAndUser(Long postId, User user);

    Long countByPostId(Long postId);

    void deleteAllByUser(User user);

    void deleteAllByPostId(Long postId);

}
