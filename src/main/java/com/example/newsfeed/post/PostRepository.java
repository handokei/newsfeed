package com.example.newsfeed.post;

import com.example.newsfeed.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PostRepository extends JpaRepository<Post, Long> {


    Page<Post> findAllByModifiedAtBetween(LocalDate periodStart, LocalDate periodEnd, Pageable pageable);
}
