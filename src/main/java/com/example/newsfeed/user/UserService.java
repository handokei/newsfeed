package com.example.newsfeed.user;


import com.example.newsfeed.common.Const;
import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.exception.EmailAlreadyExistsException;
import com.example.newsfeed.exception.EmailNotFoundException;
import com.example.newsfeed.exception.LoginUserPasswordException;
import com.example.newsfeed.user.dto.CreateUserRequestDto;
import com.example.newsfeed.user.dto.UserLoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;


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
                .orElseThrow(() -> new EmailNotFoundException());

        //비밀번호 불일치시, 401에러
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginUserPasswordException();
        }

        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, user);
        return new UserLoginResponseDto(user.getUsername(),session.getId());
    }

}
