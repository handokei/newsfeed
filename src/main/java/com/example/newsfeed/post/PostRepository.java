package com.example.newsfeed.post;

import com.example.newsfeed.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
