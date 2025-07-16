package com.workout.heavylift.service;

import com.workout.heavylift.dto.board.CommentResponse;
import com.workout.heavylift.dto.board.CreateCommentRequest;
import com.workout.heavylift.dto.board.UpdateCommentRequest;

import java.util.List;

public interface CommentService {

    CommentResponse create(Long postId, CreateCommentRequest request);

    void delete(Long commentId);

    List<CommentResponse> getCommentsByPost(Long postId);

    void validateAuthor(Long commentId, Long userId);

    CommentResponse update(Long commentId, UpdateCommentRequest request);
}
