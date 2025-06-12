package com.example.newsfeed.post;


import com.example.newsfeed.common.config.jwt.JwtService;
import com.example.newsfeed.exception.PostNotFoundException;
import com.example.newsfeed.exception.UserNeedLoginException;
import com.example.newsfeed.exception.UserNotFoundException;
import com.example.newsfeed.post.dto.CreatePostRequestDto;
import com.example.newsfeed.post.dto.PostListResponseDto;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.user.User;
import com.example.newsfeed.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;


@Service
@Transactional
public class PostService {
    private final UserRepository userRepository;
    PostRepository postRepository;
    JwtService jwtService;

    public PostService(PostRepository postRepository, JwtService jwtService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    //게시물 저장
    @Transactional
    public PostResponseDto save(CreatePostRequestDto requestDto,
                                HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        //로그인 확인 예외
        if (authorization == null || !authorization.startsWith("Bearer ")){
            throw new UserNeedLoginException();
        }
        String token = authorization.substring(7);

        long userId = jwtService.getUserIdFromToken(token);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Post savePost = Post.create(requestDto.getTitle(), requestDto.getContent(), user);
        Post saved = (Post) postRepository.save(savePost);
        return PostResponseDto.from(saved);
    }


    //게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        //로그인 확인 예외
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UserNeedLoginException();
        }
        String token = authorization.substring(7);
        long userId = jwtService.getUserIdFromToken(token);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

                //게시글 유무 확인 예외처리, 조회
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        //본인 게시글 확인
        if (!post.getUser().getId().equals(user.getId())) {
            throw new PostNotFoundException();
        }

        //수정
        post.update(requestDto.getTitle(), requestDto.getContent());

        return PostResponseDto.from(post);
    }

    //게시글 전체조회
    @Transactional(readOnly = true)
    public Page<PostListResponseDto> pagePosts(Pageable pageable,
                                               LocalDate periodStart,
                                               LocalDate periodEnd) {

        //기간 조건이 필요할 때 사용
        if (periodStart != null && periodEnd != null) {
            return postRepository.findAllByModifiedAtBetween(periodStart,periodEnd,pageable)
                    .map(PostListResponseDto::listDto);
        }
        //기간 조건 없이 검색 될 때 사용
        return postRepository.findAll(pageable)
                .map(PostListResponseDto::listDto);
    }

    //단건조회
    @Transactional(readOnly = true)
    public PostResponseDto onePost(Long id) {
        Optional<Post> foundId = postRepository.findById(id);

        //게시글 유무 예외처리
        if (foundId.isEmpty()) {
            throw new PostNotFoundException();
        }
        Post post = foundId.get();

        return PostResponseDto.from(post);
    }

    //게시물 삭제
    public void deletePost(Long id,HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        //로그인 확인 예외
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UserNeedLoginException();
        }
        String token = authorization.substring(7);
        long userId = jwtService.getUserIdFromToken(token);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        //게시글 유무 확인 예외처리, 조회
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        //본인 게시글 확인
        if (!post.getUser().getId().equals(user.getId())) {
            throw new PostNotFoundException();
        }

        postRepository.delete(post);
    }
}
