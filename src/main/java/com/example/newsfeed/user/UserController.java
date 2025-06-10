package com.example.newsfeed.user;



import com.example.newsfeed.user.dto.CreateUserRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.CacheResponse;

@RestController
@RequestMapping("/users")

public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> create(@RequestBody CreateUserRequestDto requestDto) {
        userService.save(requestDto);
        return ResponseEntity.ok("회원가입 축하드립니다.("+requestDto.getUsername()+") 님");
    }


}
