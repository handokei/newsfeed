package com.example.newsfeed.user;


import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.common.config.jwt.JwtService;
import com.example.newsfeed.exception.*;
import com.example.newsfeed.user.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    //회원가입
    @Transactional
    public void save(CreateUserRequestDto requestDto){
        //체크예외 -> 이메일 중복
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(requestDto.getEmail());
        }
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getUsername(), requestDto.getEmail(), encodePassword);

        userRepository.save(user);


    }


    //로그인
    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto, HttpServletRequest request) {
        //이메일 불일치시,401에러
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(EmailNotFoundException::new);

        //비밀번호 불일치시, 401에러
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new LoginUserPasswordException();
        }
        String token = jwtService.createJwt(user.getId());
        return new UserLoginResponseDto(user.getId(), user.getUsername(),token);
    }

    //프로필 조회
    @Transactional(readOnly = true)
    public UserResponseDto readUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponseDto(user.getUsername());
    }

    //프로필 수정(비밀번호 변경)
    @Transactional
    public void updatePassword(UserPasswordUpdateRequestDto requestDto,
                               HttpServletRequest request) {
        //토큰 추출
        String authorization = request.getHeader("Authorization");
        //꺼낸 토큰이 동일한지 확인하는 예외처리
        if (authorization == null || !authorization.startsWith("Bearer ")){
            throw new UserNeedLoginException();
        }
        String token = authorization.substring(7);//Bearer 빼고 추출하기

        //사용자 ID 파싱
        long userId = jwtService.getUserIdFromToken(token);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        //비밀번호 불일치시 예외처리
        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword()))
            throw new UserWrongPasswordException();

       //동일한 비밀번호로 변경 요청시 예외처리
        if (requestDto.getOldPassword().equals(requestDto.getNewPassword())) {
           throw new UserSamePasswordException();
       }
       String encode = passwordEncoder.encode(requestDto.getNewPassword());
        user.updatePassword(encode);
        userRepository.save(user);

    }
}
