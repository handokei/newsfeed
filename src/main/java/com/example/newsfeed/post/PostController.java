package com.example.newsfeed.post;

import com.example.newsfeed.dto.ApiResponse;
import com.example.newsfeed.post.dto.CreatePostRequestDto;
import com.example.newsfeed.post.dto.PostListResponseDto;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    PostService postService;
    UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    //게시글 생성
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> create(@Valid @RequestBody CreatePostRequestDto requestDto,
                                                               HttpServletRequest request) {
        PostResponseDto responseDto = postService.save(requestDto, request);

        return new ResponseEntity<>(new ApiResponse<>(201,"게시글이 작성완료 됐습니다.",responseDto), HttpStatus.CREATED);
    }

    //게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> update(
            @PathVariable Long id,
            @Valid@RequestBody PostRequestDto requestDto,
            HttpServletRequest request){
        PostResponseDto responseDto = postService.updatePost(id,requestDto, request);

        return new ResponseEntity<>(new ApiResponse<>(200,"게시글 수정 성공!",responseDto),HttpStatus.OK);
    }

    //게시글 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostListResponseDto>>> readAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
                    HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostListResponseDto> responseDto = postService.pagePosts(pageable);

        return new ResponseEntity<>( new ApiResponse<>(200,"게시글 전체 조회 완료!",responseDto),HttpStatus.OK);
    }

    //게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> readOnePost(@PathVariable Long id){
        PostResponseDto responseDto = postService.onePost(id);

        return new ResponseEntity<>(new ApiResponse<>(200,"게시글 단건 조회 완료!",responseDto),HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id,
                                                    HttpServletRequest request){
        postService.deletePost(id,request);
        return new ResponseEntity<>(new ApiResponse<>(200,"게시글 정상 삭제 완료!",null),HttpStatus.OK);
    }

}
