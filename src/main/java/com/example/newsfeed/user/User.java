package com.example.newsfeed.user;

import com.example.newsfeed.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username",nullable = false,length = 30)
    @NotNull
    private String username;

    @Column(name = "email",nullable = false,updatable = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    //jpa사용예정
     public User() {

    }

    protected User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    //추후 비밀번호 요건 추가해야함
    private boolean validPasswordFormat(String password){

        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,}$");
    }
}
