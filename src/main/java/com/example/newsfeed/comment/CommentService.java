package com.example.newsfeed.comment;

import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.dto.CreateCommentRequestDto;
import com.example.newsfeed.common.config.jwt.JwtService;
import com.example.newsfeed.exception.PostNotFoundException;
import com.example.newsfeed.exception.UserNeedLoginException;
import com.example.newsfeed.exception.UserNotFoundException;
import com.example.newsfeed.post.Post;
import com.example.newsfeed.post.PostRepository;
import com.example.newsfeed.user.User;
import com.example.newsfeed.user.UserRepository;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    CommentRepository commentRepository;
    PostRepository postRepository;
    JwtService jwtService;

    public CommentService(UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.jwtService = jwtService;
    }

    //댓글 저장
    public CommentResponseDto save(Long id,CreateCommentRequestDto requestDto, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        //로그인 확인 예외
        if (authorization == null || !authorization.startsWith("Bearer ")){
            throw new UserNeedLoginException();
        }
        String token = authorization.substring(7);

        long userId = jwtService.getUserIdFromToken(token);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        Comment comment = Comment.create(requestDto.getContent(),user,post);
        commentRepository.save(comment);

        return CommentResponseDto.from(comment);
    }
    //댓글 조회(사용자 기준)
    public List<CommentResponseDto> userAllComment(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        //로그인 확인 예외
        if (authorization == null || !authorization.startsWith("Bearer ")){
            throw new UserNeedLoginException();
        }
        String token = authorization.substring(7);

        long userId = jwtService.getUserIdFromToken(token);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<Comment> comments = commentRepository.findByUser(user);

        return comments.stream().map(CommentResponseDto::from).toList();
    }

    public List<CommentResponseDto> postAllComment(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream().map(CommentResponseDto::from).toList();
    }

    //


}
