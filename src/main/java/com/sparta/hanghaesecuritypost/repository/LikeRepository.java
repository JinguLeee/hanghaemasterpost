package com.sparta.hanghaesecuritypost.repository;


import com.sparta.hanghaesecuritypost.entity.Like;
import com.sparta.hanghaesecuritypost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByIndexAndLikeIdAndUser(int index, Long likeId, User user);

    Long countByIndexAndLikeId(int index, Long likeId);

    void deleteAllByIndexAndLikeId(int index, Long likeId);
    void deleteAllByUser(User user);
    void deleteAllByPostId(Long postId);

}
