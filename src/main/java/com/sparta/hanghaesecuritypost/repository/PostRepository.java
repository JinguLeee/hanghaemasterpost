package com.sparta.hanghaesecuritypost.repository;

import com.sparta.hanghaesecuritypost.entity.Post;
import com.sparta.hanghaesecuritypost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    Optional<Post> findByIdAndUser(Long id, User user);
    List<Post> findAllByUser(User user);

    void deleteAllByUser(User user);
}
