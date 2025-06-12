package com.example.newsfeed.user;



import com.example.newsfeed.dto.ApiResponse;
import com.example.newsfeed.user.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")

public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<CreateUserResponseDto>> create(@RequestBody CreateUserRequestDto requestDto) {
        userService.save(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(201,"회원 가입을 축하드립니다."+requestDto.getUsername()+"님",null));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> loginUser(@Valid @RequestBody UserLoginRequestDto requestDto ,
                                                 HttpServletRequest request) {
        UserLoginResponseDto loginResponseDto = userService.login(requestDto, request);
        return ResponseEntity.ok(new ApiResponse<>(200,"로그인 성공!",loginResponseDto));
    }

    //프로필 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> userProfile(@PathVariable Long id) {
        UserResponseDto responseDto = userService.readUser(id);

        return ResponseEntity.ok(new ApiResponse<>(200, "프로필 조회 성공",responseDto));
    }

    //프로필 수정(비밀번호 수정)
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<Void>> update(@Valid@RequestBody UserPasswordUpdateRequestDto requestDto,
                                                    HttpServletRequest request) {
        userService.updatePassword(requestDto,request);

        return new ResponseEntity<>(new ApiResponse<>(200,"비밀번호 수정 성공!",null),HttpStatus.OK);
    }

}
