package com.example.newsfeed.user;



import com.example.newsfeed.common.Const;
import com.example.newsfeed.user.dto.CreateUserRequestDto;
import com.example.newsfeed.user.dto.UserLoginRequestDto;
import com.example.newsfeed.user.dto.UserLoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
        return ResponseEntity.ok("회원가입 축하드립니다.("+requestDto.getUsername()+")님");
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> loginUser( @Valid@RequestBody UserLoginRequestDto requestDto ,
                                             HttpServletRequest request) {
        UserLoginResponseDto loginResponseDto = userService.login(requestDto.getEmail(), requestDto.getPassword(), request);
        return ResponseEntity.ok(loginResponseDto.getSessionId());
    }


}
