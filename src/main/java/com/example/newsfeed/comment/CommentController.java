package com.example.newsfeed.comment;

import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.dto.CreateCommentRequestDto;
import com.example.newsfeed.common.config.jwt.JwtService;
import com.example.newsfeed.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //댓글 생성
    @PostMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<CommentResponseDto>> create(@PathVariable Long id,
            @Valid@RequestBody CreateCommentRequestDto requestDto, HttpServletRequest request) {
        CommentResponseDto save = commentService.save(id,requestDto, request);
        return new ResponseEntity<>(new ApiResponse<>(201,"댓글 생성 완료!",save), HttpStatus.CREATED);
    }

    //댓글 조회(사용자기준)
    @GetMapping("/users/me/comments")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> readAllUserComment(HttpServletRequest request) {
        List<CommentResponseDto> comment = commentService.userAllComment(request);

        return ResponseEntity.ok(new ApiResponse<>(200,"내가 작성한 댓글 조회 완료!",comment));
    }

    //댓글 조회(게시물 시준)
    @GetMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> readAllPostComment(@PathVariable Long id) {
        List<CommentResponseDto> comment = commentService.postAllComment(id);

        return ResponseEntity.ok(new ApiResponse<>(200,"내가 작성한 댓글 조회 완료!",comment));
    }
}
