package com.example.newsfeed.user;


import com.example.newsfeed.common.Const;
import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.exception.*;
import com.example.newsfeed.user.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
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
    public UserLoginResponseDto login(String email, String password, HttpServletRequest request) {
        //이메일 불일치시,401에러
        User user = userRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);

        //비밀번호 불일치시, 401에러
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginUserPasswordException();
        }

        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, user);
        return new UserLoginResponseDto(user.getId(), user.getUsername(),session.getId());
    }

    //프로필 조회
    public UserResponseDto readUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponseDto(user.getUsername());
    }

    //프로필 수정(비밀번호 변경)
    public void updatePassword(UserPasswordUpdateRequestDto requestDto,
                               HttpServletRequest request) {
        //로그인 확인 예외처리
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Const.LOGIN_USER) == null){
            throw new UserNeedLoginException();
        }
        User loginUser = (User) session.getAttribute(Const.LOGIN_USER);
        User user = userRepository.findById(loginUser.getId())
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
        System.out.println("세션 userId: " + session.getAttribute("userId"));
        System.out.println("oldPassword: " + requestDto.getOldPassword());
        System.out.println("newPassword: " + requestDto.getNewPassword());

    }
}
