package com.example.newsfeed.comment;

import com.example.newsfeed.post.Post;
import com.example.newsfeed.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser(User user);

    List<Comment> findByPost(Post post);
}
