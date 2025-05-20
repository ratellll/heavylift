package com.workout.heavylift.controller;


import com.workout.heavylift.config.SecurityUtil;
import com.workout.heavylift.dto.board.*;
import com.workout.heavylift.service.BoardPostService;
import com.workout.heavylift.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardPostController {

    private final BoardPostService boardPostService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<BoardPostResponse> createPost(@RequestBody CreateBoardPostRequest request) {
        return ResponseEntity.ok(boardPostService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardPostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(boardPostService.getPost(id));
    }

    @GetMapping
    public ResponseEntity<List<BoardPostResponse>> getAllPost() {
        return ResponseEntity.ok(boardPostService.getAllPosts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardPostResponse> updatePost(@PathVariable Long id, @RequestBody UpdateBoardPostRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        boardPostService.validateAuthor(id, userId);
        return ResponseEntity.ok(boardPostService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Long userId = SecurityUtil.getCurrentUserId();
        boardPostService.validateAuthor(id, userId);
        boardPostService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long id) {
        boardPostService.likePost(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Void> unlikePost(@PathVariable Long id) {
        boardPostService.unlikePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<Boolean> isLiked(@PathVariable Long id) {
        return ResponseEntity.ok(boardPostService.isLiked(id));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId, @RequestBody CreateCommentRequest request) {
        return ResponseEntity.ok(commentService.create(postId, request));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId, @RequestBody UpdateCommentRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        commentService.validateAuthor(commentId, userId);
        return ResponseEntity.ok(commentService.update(commentId, request));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        Long userId = SecurityUtil.getCurrentUserId();
        commentService.validateAuthor(commentId, userId);
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }
}
