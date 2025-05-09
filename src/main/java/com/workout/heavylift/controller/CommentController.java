package com.workout.heavylift.controller;


import com.workout.heavylift.domain.BoardPost;
import com.workout.heavylift.domain.Comment;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.dto.board.CommentResponse;
import com.workout.heavylift.dto.board.CreateCommentRequest;
import com.workout.heavylift.repository.BoardPostRepository;
import com.workout.heavylift.repository.CommentRepository;
import com.workout.heavylift.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final BoardPostRepository boardPostRepository;
    private final UserRepository userRepository;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId,
                                                      @Valid @RequestBody CreateCommentRequest request) {
        BoardPost post = boardPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment comment = commentRepository.save(request.toEntity(post, user));
        return ResponseEntity.ok(CommentResponse.fromEntity(comment));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        BoardPost post = boardPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        List<CommentResponse> comments = post.getComments().stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }
}
