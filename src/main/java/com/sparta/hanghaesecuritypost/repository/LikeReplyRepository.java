package com.sparta.hanghaesecuritypost.repository;

import com.sparta.hanghaesecuritypost.entity.LikeReply;
import com.sparta.hanghaesecuritypost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeReplyRepository extends JpaRepository<LikeReply, Long> {
    LikeReply findByReplyIdAndUser(Long replyId, User user);

    Long countByReplyId(Long replyId);

    void deleteAllByReplyId(Long replyId);
    void deleteAllByUserId(Long userId);
}
