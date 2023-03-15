package com.sparta.hanghaesecuritypost.repository;

import com.sparta.hanghaesecuritypost.entity.Reply;
import com.sparta.hanghaesecuritypost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByIdAndUser(Long id, User user);

    List<Reply> findAllByPostId(Long id);

    List<Reply> findAllByUser(User user);

    void deleteAllByPostId(Long id);

    void deleteAllByUser(User user);
}
