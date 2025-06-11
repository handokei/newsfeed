package com.example.newsfeed.post;

import com.example.newsfeed.common.Const;
import com.example.newsfeed.exception.PostNotFoundException;
import com.example.newsfeed.exception.UserNeedLoginException;
import com.example.newsfeed.post.dto.CreatePostRequestDto;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto save(CreatePostRequestDto requestDto,
                                HttpServletRequest request){
        HttpSession session = request.getSession();

        //로그인 확인 예외
        if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
            throw new UserNeedLoginException();
        }

        User user = (User) session.getAttribute(Const.LOGIN_USER);

        Post savePost = Post.create(requestDto.getTitle(), requestDto.getContent(), user);
        Post saved = (Post) postRepository.save(savePost);
        return PostResponseDto.from(saved);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();

        //로그인 확인 예외
        if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
            throw new UserNeedLoginException();
        }

        User user = (User) session.getAttribute(Const.LOGIN_USER);

        //게시글 유무 확인 예외처리, 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException());

        //본인 게시글 확인
        if (!post.getUser().getId().equals(user.getId())) {
            throw new PostNotFoundException();
        }

        //수정
        post.update(requestDto.getTitle(), requestDto.getContent());

        return PostResponseDto.from(post);
    }
}
