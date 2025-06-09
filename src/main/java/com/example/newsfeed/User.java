package com.example.newsfeed;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = "user_id")
    private Long id;
}
